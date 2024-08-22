package com.phutl.meowpet.modules.membership;

public interface MembershipService {
    public void createMembership(String token) throws Exception;
    public void updateMembership(String token) throws Exception;
    public void deleteMembership(String token) throws Exception;
}
