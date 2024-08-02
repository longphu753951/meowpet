package com.phutl.meowpet.modules.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phutl.meowpet.core.components.JwtTokenUtil;
import com.phutl.meowpet.core.filters.JwtTokenFilter;
import com.phutl.meowpet.modules.database.User;
import com.phutl.meowpet.modules.user.dto.UserDTO;
import com.phutl.meowpet.modules.user.dto.UserLoginDTO;
import com.phutl.meowpet.modules.user.imlp.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password and retype password are not the same");
            }
            userService.createUser(userDTO);
            return ResponseEntity.ok("Register successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult result, HttpServletRequest request) {
        try {
            String token = userService.login(userLoginDTO);
            String userAgent = request.getHeader("User-Agent");
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetail(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            String email = jwtTokenUtil.extractEmail(jwtToken);
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
