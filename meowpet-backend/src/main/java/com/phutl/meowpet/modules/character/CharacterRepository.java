package com.phutl.meowpet.modules.character;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phutl.meowpet.modules.database.Character;
import com.mongodb.lang.NonNull;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long>, CustomCharacterRepository {
    Optional<Character> findByName(@NonNull String name);
}
