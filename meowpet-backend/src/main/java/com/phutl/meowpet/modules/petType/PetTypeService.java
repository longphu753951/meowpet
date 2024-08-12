package com.phutl.meowpet.modules.petType;

import java.util.List;

import com.phutl.meowpet.modules.database.PetType;

public interface PetTypeService {
    PetType getPetTypeById(long id);
    PetType getPetTypeByName(String name);
    PetType createPetType(PetType petType);
    PetType updatePetType(PetType petType);
    void deletePetType(long id);
    List<PetType> getAllPetTypes();
    
}
