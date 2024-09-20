package com.phutl.meowpet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.phutl.meowpet.entity.Customer;
import com.phutl.meowpet.modules.customer.CreateCustomerDTO;
import com.phutl.meowpet.services.CustomerService;
import com.phutl.meowpet.repository.CustomerRepository;
import com.phutl.meowpet.services.EmailService;
import com.phutl.meowpet.services.OtpService;
import com.phutl.meowpet.services.MembershipService;

@Service
public class CustomerServiceImpl extends UserServiceImpl<Customer> implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private MembershipService membershipService;

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
    public Customer confirmOtpAndRegister(CreateCustomerDTO customerDTO, String otp) throws Exception {
        Customer newCustomer = super.confirmOtpAndRegister(customerDTO, otp, Customer.class);
        newCustomer.setRoles(customerDTO.getRoles());
        newCustomer.setAddress(customerDTO.getAddress());
        newCustomer.setBio(customerDTO.getBio());
        newCustomer.setFacebookAccountId(customerDTO.getFacebookAccountId());
        newCustomer.setGoogleAccountId(customerDTO.getGoogleAccountId());
        membershipService.createMembership(newCustomer);
        // Check if having accountId, passwork will be unrequired
        if (customerDTO.getFacebookAccountId() == 0 && customerDTO.getGoogleAccountId() == 0) {
            String password = customerDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newCustomer.setPassword(encodedPassword);
        }
        return customerRepository.save(newCustomer);
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
