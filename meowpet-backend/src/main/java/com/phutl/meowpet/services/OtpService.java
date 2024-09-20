package com.phutl.meowpet.services;

public interface OtpService {
    public String generateOTP(String email);
    public boolean verifyOTP(String email, String otp);
}
