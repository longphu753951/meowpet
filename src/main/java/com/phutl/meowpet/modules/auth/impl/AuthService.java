package com.phutl.meowpet.modules.auth.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.phutl.meowpet.core.config.JwtTokenUtil;
import com.phutl.meowpet.modules.auth.IAuthService;
import com.phutl.meowpet.modules.database.Token;
import com.phutl.meowpet.modules.database.User;
import com.phutl.meowpet.modules.token.ITokenService;
import com.phutl.meowpet.modules.user.UserRepository;
import com.phutl.meowpet.shared.common.TokenType;

public class AuthService implements IAuthService {

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String generateAccessToken(User user) {
        String accessToken = jwtTokenUtil.generateToken(user);
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(15); // Set access token expiry time
        tokenService.saveToken(accessToken, TokenType.ACCESS, user, expirationDate);
        return accessToken;
    }

    @Override
    public String generateRefreshToken(User user) {
        String refreshToken = jwtTokenUtil.generateToken(user); // Generate refresh token using jwtTokenUtil
    LocalDateTime expirationDate = LocalDateTime.now().plusDays(30); // Set refresh token expiry time
    tokenService.saveToken(refreshToken, TokenType.REFRESH, user, expirationDate);
        return refreshToken;
    }

    @Override
    public String refreshAccessToken(String refreshToken) throws Exception {
        Optional<Token> optionalToken = tokenService.findByToken(refreshToken);
        if (optionalToken.isEmpty() || optionalToken.get().isRevoked() || optionalToken.get().isExpired()) {
            throw new Exception("Invalid or expired refresh token");
        }

        Token existingToken = optionalToken.get();
        User user = existingToken.getUser();
        tokenService.revokeToken(refreshToken);

        return generateAccessToken(user);
    }

}
