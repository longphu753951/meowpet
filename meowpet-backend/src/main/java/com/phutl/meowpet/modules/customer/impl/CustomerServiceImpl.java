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
import com.phutl.meowpet.shared.common.Role;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;

@Service
public class CustomerServiceImpl extends UserServiceImpl<Customer> implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Manually call the superclass constructor
    public CustomerServiceImpl(OtpService otpService, PasswordEncoder passwordEncoder, EmailService emailService,
            CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer confirmOtpAndRegister(CreateCustomerDTO customerDTO, String otp) {
        Customer newUser = super.confirmOtpAndRegister(customerDTO, otp);
        newUser.setRoles(customerDTO.getRoles());
        newUser.setAddress(customerDTO.getAddress());
        newUser.setBio(customerDTO.getBio());
        newUser.setFacebookAccountId(customerDTO.getFacebookAccountId());
        newUser.setGoogleAccountId(customerDTO.getGoogleAccountId());

        // Check if having accountId, passwork will be unrequired
        if (customerDTO.getFacebookAccountId() == 0 && customerDTO.getGoogleAccountId() == 0) {
            String password = customerDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return customerRepository.save(newUser);
    }

    @Override
    public Object getCoupon(String token) throws Exception {
        return null;
    }

    @Override
    public void register(CreateCustomerDTO createCustomerDTO) throws Exception {
        super.register(createCustomerDTO);
    }

}
