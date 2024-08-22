package com.phutl.meowpet.modules.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phutl.meowpet.core.components.JwtTokenUtil;
import com.phutl.meowpet.core.filters.JwtTokenFilter;
import com.phutl.meowpet.modules.customer.dto.CreateCustomerDTO;
import com.phutl.meowpet.modules.database.Token;
import com.phutl.meowpet.modules.database.User;
import com.phutl.meowpet.modules.token.TokenService;
import com.phutl.meowpet.modules.token.dto.RefreshTokenDTO;
import com.phutl.meowpet.modules.token.impl.TokenServiceImpl;
import com.phutl.meowpet.modules.user.dto.UserDTO;
import com.phutl.meowpet.modules.user.dto.UserLoginDTO;
import com.phutl.meowpet.modules.user.imlp.UserServiceImpl;
import com.phutl.meowpet.shared.responses.ResponseObject;
import com.phutl.meowpet.shared.responses.user.LoginResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password and retype password are not the same");
            }
            userService.register(userDTO);
            return ResponseEntity.ok("OTP sent to your email");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirm-registration")
    public ResponseEntity<String> confirmRegistration(@RequestBody UserDTO userRegisterDTO, @RequestParam String otp) {
        try {
            // userService.confirmOtpAndRegister(userRegisterDTO, otp);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult result,
            HttpServletRequest request) {
        try {
            String token = userService.login(userLoginDTO);
            User user = userService.getUserDetailFromToken(token);
            Token newToken = tokenService.addToken(user, token, isMobileDevice(request));
            return ResponseEntity.ok(createLoginResponse("Login successfully", user, newToken));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private LoginResponse createLoginResponse(String message,User user, Token newToken) {
        return LoginResponse.builder()
                .message(message)
                .username(user.getEmail())
                .token(newToken.getToken())
                .refreshToken(newToken.getRefreshToken())
                .authorities(user.getAuthorities())
                .build();
    }

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetail(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            User user = userService.getUserDetailFromToken(jwtToken);
            return ResponseEntity.ok(user);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseObject> refreshToken(
            @Valid @RequestBody RefreshTokenDTO refreshTokenDTO
    ) throws Exception{
            User existingUser = userService.getUserDetailFromRefreshToken(refreshTokenDTO.getRefreshToken());
            Token jwtToken = tokenService.refreshToken(refreshTokenDTO.getRefreshToken(), existingUser);
            LoginResponse loginResponse = createLoginResponse("Refresh token successfully", existingUser, jwtToken);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .data(loginResponse)
                        .message(loginResponse.getMessage())
                        .status(HttpStatus.OK)
                        .build());
    }

    private boolean isMobileDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent.contains("Android") || userAgent.contains("iPhone");
    }
}
