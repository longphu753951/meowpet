package com.phutl.meowpet.modules.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phutl.meowpet.modules.database.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
