package com.phutl.meowpet.modules.customer;

import org.springframework.stereotype.Service;

import com.phutl.meowpet.modules.customer.dto.CreateCustomerDTO;
import com.phutl.meowpet.modules.database.Customer;

public interface CustomerService {
    Object getCoupon(String token) throws Exception;
    void register(CreateCustomerDTO createCustomerDTO) throws Exception;
    Customer confirmOtpAndRegister(CreateCustomerDTO customerDTO, String otp) throws Exception ;
}
