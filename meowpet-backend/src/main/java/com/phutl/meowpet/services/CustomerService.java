package com.phutl.meowpet.services;

import org.springframework.stereotype.Service;

import com.phutl.meowpet.entity.Customer;
import com.phutl.meowpet.dto.customer.CreateCustomerDTO;

public interface CustomerService {
    Object getCoupon(String token) throws Exception;
    void register(CreateCustomerDTO createCustomerDTO) throws Exception;
    Customer confirmOtpAndRegister(CreateCustomerDTO customerDTO, String otp) throws Exception ;
}
