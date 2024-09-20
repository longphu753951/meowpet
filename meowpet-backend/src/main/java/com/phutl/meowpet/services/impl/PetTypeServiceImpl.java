package com.phutl.meowpet.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phutl.meowpet.entity.PetType;
import com.phutl.meowpet.modules.petType.PetTypeDTO;
import com.phutl.meowpet.services.PetTypeService;
import com.phutl.meowpet.repository.PetTypeRepository;

@Service
public class PetTypeServiceImpl implements PetTypeService {

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Override
    public Optional<PetType> getPetTypeById(long id) {
        Optional<PetType> petType = petTypeRepository.findById(id);
        return petType;
    }

    @Override
    public Optional<PetType> getPetTypeByName(String name) {
        Optional<PetType> petType = petTypeRepository.findByName(name);
        return petType;
    }

    @Override
    public PetType createPetType(PetTypeDTO petTypeDTO) throws Exception {
        Optional<PetType> existingPetType = getPetTypeByName(petTypeDTO.getName());
        if(existingPetType.isPresent()) {
            throw new UnsupportedOperationException("PetType already exists");
        }
        PetType newPetType = PetType.builder()
                .name(petTypeDTO.getName())
                .description(petTypeDTO.getDescription())
                .image(petTypeDTO.getImageUrl())
                .build();
        petTypeRepository.save(newPetType);
        return newPetType;
    }

    @Override
    public PetType updatePetType(PetTypeDTO petTypeDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePetType'");
    }

    @Override
    public void deletePetType(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePetType'");
    }

    @Override
    public List<PetType> getAllPetTypes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPetTypes'");
    }
    
}
