package com.phutl.meowpet.modules.token;

import java.time.LocalDateTime;
import java.util.Optional;

import com.phutl.meowpet.modules.database.Token;
import com.phutl.meowpet.modules.database.User;
import com.phutl.meowpet.shared.common.TokenType;

public interface ITokenService {
    public void saveToken(String token, TokenType tokenType, User user, LocalDateTime expirationDate);
    public Optional<Token> findByToken(String token);
    public void revokeToken(String token);
}
