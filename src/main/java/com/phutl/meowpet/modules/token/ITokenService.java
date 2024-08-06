package com.phutl.meowpet.modules.token;

import java.time.LocalDateTime;
import java.util.Optional;

import com.phutl.meowpet.modules.database.Token;
import com.phutl.meowpet.modules.database.User;
import com.phutl.meowpet.shared.common.TokenType;

public interface ITokenService {
    Token refreshToken(String refreshToken, User user) throws Exception;
    public void revokeToken(String token);
    public Token addToken(User user, String token, boolean isMobileDevice);
}
