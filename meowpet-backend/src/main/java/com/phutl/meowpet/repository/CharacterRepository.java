package com.phutl.meowpet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.lang.NonNull;
import com.phutl.meowpet.entity.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long>, CustomCharacterRepository {
    Optional<Character> findByName(@NonNull String name);
}
