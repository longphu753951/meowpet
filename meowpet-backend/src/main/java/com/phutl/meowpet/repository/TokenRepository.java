package com.phutl.meowpet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phutl.meowpet.entity.Token;
import com.phutl.meowpet.entity.User;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    List<Token> findByUser(User user);
    Token findByRefreshToken(String refreshToken);
}
