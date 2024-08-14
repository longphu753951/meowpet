package com.phutl.meowpet.modules.petType;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.phutl.meowpet.modules.database.PetType;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {
    Optional<PetType> findByName(String name);
}
