package com.phutl.meowpet.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.phutl.meowpet.components.JwtTokenUtil;
import com.phutl.meowpet.entity.Token;
import com.phutl.meowpet.entity.User;
import com.phutl.meowpet.repository.UserRepository;
import com.phutl.meowpet.services.AuthService;
import com.phutl.meowpet.services.TokenService;
import com.phutl.meowpet.shared.common.TokenType;

public class AuthServiceImpl implements AuthService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String generateAccessToken(User user) {
        String accessToken = jwtTokenUtil.generateToken(user);
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(15); // Set access token expiry time
        // tokenService.saveToken(accessToken, TokenType.ACCESS, user, expirationDate);
        return accessToken;
    }

    @Override
    public String generateRefreshToken(User user) {
        String refreshToken = jwtTokenUtil.generateToken(user); // Generate refresh token using jwtTokenUtil
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(30); // Set refresh token expiry time
        return refreshToken;
    }

    

}
