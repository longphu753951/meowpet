package com.phutl.meowpet.modules.customer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phutl.meowpet.modules.customer.CustomerRepository;
import com.phutl.meowpet.modules.customer.CustomerService;
import com.phutl.meowpet.modules.customer.dto.CreateCustomerDTO;
import com.phutl.meowpet.modules.database.Customer;
import com.phutl.meowpet.modules.email.EmailService;
import com.phutl.meowpet.modules.otp.OtpService;
import com.phutl.meowpet.modules.user.imlp.UserServiceImpl;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl extends UserServiceImpl<Customer> implements CustomerService  {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    @Override
    public Object getCoupon(String token) throws Exception {
        return null;
    }
    
    @Override
    public Customer confirmOtpAndREgister(CreateCustomerDTO createCustomerDTO , String otp) {
        super(otpService, passwordEncoder, emailService);
        return null;
    }
}
