package com.phutl.meowpet.modules.membership;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phutl.meowpet.modules.database.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long> {}
