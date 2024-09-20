package com.phutl.meowpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phutl.meowpet.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
