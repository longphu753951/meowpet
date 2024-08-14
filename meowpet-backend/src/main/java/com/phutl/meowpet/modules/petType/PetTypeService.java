package com.phutl.meowpet.modules.petType;

import java.util.List;
import java.util.Optional;
import com.phutl.meowpet.modules.database.PetType;
import com.phutl.meowpet.modules.petType.dto.PetTypeDTO;

public interface PetTypeService {
    Optional<PetType> getPetTypeById(long id) throws Exception;
    Optional<PetType> getPetTypeByName(String name ) throws Exception;
    PetType createPetType(PetTypeDTO petTypeDTO) throws Exception;
    PetType updatePetType(PetTypeDTO petTypeDTO);
    void deletePetType(long id);
    List<PetType> getAllPetTypes();
    
}
