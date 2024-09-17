package com.phutl.meowpet.modules.membership.impl;

import org.springframework.stereotype.Service;

import com.phutl.meowpet.modules.database.Customer;
import com.phutl.meowpet.modules.database.Membership;
import com.phutl.meowpet.modules.membership.MembershipService;
import com.phutl.meowpet.shared.common.Rank;

@Service
public class MembershipServiceImpl implements MembershipService {
    @Override
    public void createMembership(Customer customer) throws Exception {
        Membership membership = Membership.builder().customer(customer).points(0).rank(Rank.getRankByPoints(0)).build();
        customer.setMembership(membership);
    }


    @Override
    public void updateMembership(Customer customer) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMembership'");
    }

    @Override
    public void deleteMembership(Customer customer) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMembership'");
    }
    
}
