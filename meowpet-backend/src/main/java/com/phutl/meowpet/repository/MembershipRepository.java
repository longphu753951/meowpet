package com.phutl.meowpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phutl.meowpet.entity.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long> {}
