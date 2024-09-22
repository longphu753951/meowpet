package com.phutl.meowpet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phutl.meowpet.dto.customer.CreateCustomerDTO;
import com.phutl.meowpet.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateCustomerDTO createCustomerDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if (!createCustomerDTO.getPassword().equals(createCustomerDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password and retype password are not the same");
            }
            customerService.register(createCustomerDTO);
            return ResponseEntity.ok("OTP sent to your email");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirm-registration")
    public ResponseEntity<String> confirmRegistration(@RequestBody CreateCustomerDTO createCustomerDTO, @RequestParam String otp) {
        try {
            customerService.confirmOtpAndRegister(createCustomerDTO, otp);
            return ResponseEntity.ok("Customer registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
