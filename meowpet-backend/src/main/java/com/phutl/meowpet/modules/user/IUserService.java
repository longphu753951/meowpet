package com.phutl.meowpet.modules.user;

import com.phutl.meowpet.core.exceptions.DataNotFoundException;
import com.phutl.meowpet.modules.database.User;
import com.phutl.meowpet.modules.user.dto.UserDTO;
import com.phutl.meowpet.modules.user.dto.UserLoginDTO;

public interface IUserService {
    User confirmOtpAndRegister(UserDTO userDTO, String otp) throws Exception;
    String login(UserLoginDTO userLoginDTO) throws Exception;
    User getUserDetailFromRefreshToken(String refreshToken) throws Exception;
    User getUserDetailFromToken(String token) throws Exception;
    void register(UserDTO userDTO);
}
