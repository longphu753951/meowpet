package com.phutl.meowpet.services.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phutl.meowpet.components.JwtTokenUtil;
import com.phutl.meowpet.exceptions.DataNotFoundException;
import com.phutl.meowpet.modules.user.UserDTO;
import com.phutl.meowpet.modules.user.UserLoginDTO;
import com.phutl.meowpet.entity.Token;
import com.phutl.meowpet.entity.User;
import com.phutl.meowpet.services.EmailService;
import com.phutl.meowpet.services.OtpService;
import com.phutl.meowpet.services.UserService;
import com.phutl.meowpet.repository.TokenRepository;
import com.phutl.meowpet.repository.UserRepository;
import com.phutl.meowpet.shared.exceptions.InvalidOtpException;

import jakarta.transaction.Transactional;

@Service
public abstract class UserServiceImpl<T extends User> implements UserService<T> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public <T extends User> T confirmOtpAndRegister(UserDTO userDTO, String otp, Class<T> clazz) {
        // Validate the OTP
        if (!otpService.verifyOTP(userDTO.getEmail(), otp)) {
            throw new InvalidOtpException("Invalid OTP");
        }

        T newUser;
        try {
            newUser = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create a new instance of " + clazz.getName(), e);
        }
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setPassword(userDTO.getPassword());
        newUser.setEmail(userDTO.getEmail());
        newUser.setRoles(userDTO.getRoles());
        // // Ensure roles are set, default to Role.USER if not provided
        // // convert userDTO => user
        // User newUser = User.builder()
        // .firstName(userDTO.getFirstName())
        // .lastName(userDTO.getLastName())
        // .phoneNumber(userDTO.getPhoneNumber())
        // .password(userDTO.getPassword())
        // .email(userDTO.getEmail())
        // .roles(userDTO.getRoles())
        // .build();
        // // Check if having accountId, passwork will be unrequired
        // // if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() ==
        // 0) {
        // String password = userDTO.getPassword();
        // String encodedPassword = passwordEncoder.encode(password);
        // newUser.setPassword(encodedPassword);
        // // }
        // return userRepository.save(newUser);
        return (T) newUser;
    }

    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        return optionalUser.get();
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(userLoginDTO.getUsername());
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid email/password");
        }
        // check password
        User existingUser = optionalUser.get();
        // if (existingUser.getFacebookAccountId() == 0 &&
        // existingUser.getGoogleAccountId() == 0) {
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.getPassword())) {
            throw new BadCredentialsException("Invalid email/password");
        }
        // }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getUsername(), userLoginDTO.getPassword());
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser).toString();
    }

    @Override
    public User getUserDetailFromRefreshToken(String refreshToken) throws Exception {
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
        String email = jwtTokenUtil.extractEmail(existingToken.getToken());
        return getUserByEmail(email);
    }

    @Override
    public User getUserDetailFromToken(String token) throws Exception {
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new Exception("Token is expired");
        }
        String email = jwtTokenUtil.extractEmail(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new DataNotFoundException("User not found");
        }
    }

    @Transactional
    @Override
    public void register(UserDTO userDTO) {
        String email = userDTO.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("email has already exists");
        }
        String otp = otpService.generateOTP(email);
        emailService.sendOtp(userDTO.getEmail(), otp);
    }
}
