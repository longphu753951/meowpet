package com.phutl.meowpet.modules.token.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phutl.meowpet.modules.database.Token;
import com.phutl.meowpet.modules.database.User;
import com.phutl.meowpet.modules.token.TokenRepository;
import com.phutl.meowpet.shared.common.TokenType;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public void saveToken(String token, TokenType tokenType, User user, LocalDateTime expirationDate) {
        Token newToken = new Token();
        newToken.setToken(token);
        newToken.setTokenType(tokenType);
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
}
