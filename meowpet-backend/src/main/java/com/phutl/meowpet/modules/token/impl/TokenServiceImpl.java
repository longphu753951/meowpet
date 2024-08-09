package com.phutl.meowpet.modules.token.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.phutl.meowpet.core.components.JwtTokenUtil;
import com.phutl.meowpet.core.exceptions.DataNotFoundException;
import com.phutl.meowpet.modules.database.Token;
import com.phutl.meowpet.modules.database.User;
import com.phutl.meowpet.modules.token.TokenRepository;
import com.phutl.meowpet.modules.token.TokenService;

import jakarta.transaction.Transactional;

@Service
public class TokenServiceImpl implements TokenService {
    private static final int MAX_TOKENS = 3;
    @Autowired
    private TokenRepository tokenRepository;

    @Value("${jwt.expiration}")
    private long tokenExpiration;

    @Value("${jwt.expiration-refresh-token}")
    private long refreshTokenExpiration;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public void revokeToken(String token) {
        Optional<Token> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            Token existingToken = optionalToken.get();
            existingToken.setRevoked(true);
            tokenRepository.save(existingToken);
        }
    }

    @Transactional
    @Override
    public Token addToken(User user, String token, boolean isMobileDevice) {
        List<Token> userTokens = tokenRepository.findByUser(user);
        int tokenCount = userTokens.size();
        // check if number of tokens is exceeded the limit then delete the oldest token
        if (tokenCount >= MAX_TOKENS) {
            // check if there is a non-mobile token
            boolean hasNonMobileToken = !userTokens.stream().allMatch(Token::isMobile);
            Token tokenToDelete;
            // delete the oldest non-mobile token
            if (hasNonMobileToken) {
                tokenToDelete = userTokens.stream().filter(t -> !t.isMobile()).findFirst().get();
            } else {
                //if all token are mobile, delete the oldest token
                tokenToDelete = userTokens.get(0);
            }
            tokenRepository.delete(tokenToDelete);
        }
        long expirationInSecond = tokenExpiration * 1000L;
        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(expirationInSecond);
        Token newToken = Token.builder()
                .user(user)
                .token(token)
                .revoked(false)
                .expired(false)
                .tokenType("Bearer")
                .isMobile(isMobileDevice)
                .expirationDate(expirationDate)
                .build();

        newToken.setRefreshToken(UUID.randomUUID().toString());
        newToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(refreshTokenExpiration));
        tokenRepository.save(newToken);
        return newToken;
    }

    // It will base token on refresh token, if it exists, it will generate new token
    // and refresh token and save it to database (thank to Transactional)
    @Transactional
    @Override
    public Token refreshToken(String refreshToken, User existingUser) {

        // Check if refresh token exists
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
        if (existingToken == null) {
            throw new DataNotFoundException("Refresh token does not exist");
        }
        // Check if refresh token is expired
        if (!existingToken.getRefreshExpirationDate().isAfter(LocalDateTime.now())) {
            tokenRepository.delete(existingToken);
            throw new DataNotFoundException("Refresh token is expired");
        }
        // Generate new token
        String token = jwtTokenUtil.generateToken(existingUser);
        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(tokenExpiration);
        existingToken.setToken(token);
        existingToken.setExpirationDate(expirationDate);
        existingToken.setRefreshToken(UUID.randomUUID().toString());
        existingToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(refreshTokenExpiration));
        return existingToken;
    }
}
