package com.phutl.meowpet.modules.token.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.phutl.meowpet.modules.database.Token;
import com.phutl.meowpet.modules.database.User;
import com.phutl.meowpet.modules.token.ITokenService;
import com.phutl.meowpet.modules.token.TokenRepository;
import com.phutl.meowpet.shared.common.TokenType;

import jakarta.transaction.Transactional;

@Service
public class TokenService implements ITokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Value("${jwt.expiration}")
    private long tokenExpiration;

    public void saveToken(String token, TokenType tokenType, User user, LocalDateTime expirationDate) {
        Token newToken = new Token();
        newToken.setToken(token);
        newToken.setTokenType("Bearer");
        newToken.setUser(user);
        newToken.setExpirationDate(expirationDate);
        newToken.setRevoked(false);
        newToken.setExpired(false);
        tokenRepository.save(newToken);
    }

    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

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
    public void addToken(User user, String token, boolean isMobileDevice) {
        List<Token> userTokens = tokenRepository.findByUser(user);
        int tokenCount = userTokens.size();
        if (tokenCount >= 3) {
            boolean hasNonMobileToken = !userTokens.stream().allMatch(Token::isMobile);
            Token tokenToDelete;
            if (hasNonMobileToken) {
                tokenToDelete = userTokens.stream().filter(t -> !t.isMobile()).findFirst().get();
            } else {
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
        tokenRepository.save(newToken);
    }
}
