package com.phutl.meowpet.services;

import com.phutl.meowpet.entity.Customer;

public interface MembershipService {
    public void createMembership(Customer customer) throws Exception;
    public void updateMembership(Customer customer) throws Exception;
    public void deleteMembership(Customer customer) throws Exception;
}
