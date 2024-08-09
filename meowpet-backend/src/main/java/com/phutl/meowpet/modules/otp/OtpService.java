package com.phutl.meowpet.modules.otp;

public interface OtpService {
    public String generateOTP(String email);
    public boolean verifyOTP(String email, String otp);
}
