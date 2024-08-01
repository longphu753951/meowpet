package com.phutl.meowpet.modules.auth;

import com.phutl.meowpet.modules.database.User;

public interface IAuthService {
    public String generateAccessToken(User user);
    public String generateRefreshToken(User user);
    public String refreshAccessToken(String refreshToken) throws Exception;
    
}
