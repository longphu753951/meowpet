package com.phutl.meowpet.modules.otp.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.phutl.meowpet.modules.otp.OtpService;

@Service
public class OtpServiceImpl implements OtpService {
    private Map<String, String> otpStorage = new HashMap<>();

    public String generateOTP(String email) {
        String otp = String.valueOf((int) (Math.random() * 9000) + 1000);
        otpStorage.put(email, otp);
        return otp;
    }

    public boolean verifyOTP(String email, String otp) {
        if (otpStorage.containsKey(email) && otpStorage.get(email).equals(otp)) {
            otpStorage.remove(email);
            return true;
        }
        return false;
    }
}
