package com.phutl.meowpet.services;

import com.phutl.meowpet.entity.User;

public interface AuthService {
    public String generateAccessToken(User user);
    public String generateRefreshToken(User user);
    
}
