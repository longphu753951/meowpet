package com.phutl.meowpet.modules.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phutl.meowpet.modules.database.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
