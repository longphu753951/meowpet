package com.phutl.meowpet.modules.membership;

import com.phutl.meowpet.modules.database.Customer;

public interface MembershipService {
    public void createMembership(Customer customer) throws Exception;
    public void updateMembership(Customer customer) throws Exception;
    public void deleteMembership(Customer customer) throws Exception;
}
