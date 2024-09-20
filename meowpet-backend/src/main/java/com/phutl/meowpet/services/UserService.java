package com.phutl.meowpet.services;

import com.phutl.meowpet.exceptions.DataNotFoundException;
import com.phutl.meowpet.modules.user.UserDTO;
import com.phutl.meowpet.modules.user.UserLoginDTO;
import com.phutl.meowpet.entity.User;

public interface UserService<T extends User> {
    <T extends User> T confirmOtpAndRegister(UserDTO userDTO, String otp, Class<T> clazz) throws Exception;
    String login(UserLoginDTO userLoginDTO) throws Exception;
    User getUserDetailFromRefreshToken(String refreshToken) throws Exception;
    User getUserDetailFromToken(String token) throws Exception;
    void register(UserDTO userDTO);
    User getUserByEmail(String email);
}
