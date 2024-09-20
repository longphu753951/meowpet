package com.phutl.meowpet.services;

import java.time.LocalDateTime;
import java.util.Optional;

import com.phutl.meowpet.entity.Token;
import com.phutl.meowpet.entity.User;
import com.phutl.meowpet.shared.common.TokenType;

public interface TokenService {
    Token refreshToken(String refreshToken, User user) throws Exception;
    public void revokeToken(String token);
    public Token addToken(User user, String token, boolean isMobileDevice);
}
