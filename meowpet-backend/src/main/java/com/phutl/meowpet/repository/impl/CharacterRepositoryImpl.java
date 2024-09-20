package com.phutl.meowpet.repository.impl;

import com.phutl.meowpet.entity.Character;
import com.phutl.meowpet.repository.CustomCharacterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class CharacterRepositoryImpl implements CustomCharacterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean softDelete(Long id) {
       Character character = entityManager.find(Character.class, id);
       if(character != null) {
            character.setDeleted(true);
            entityManager.merge(character);
            return true;
       }
       return false;
    }

    @Override
    @Transactional
    public boolean hardDelete(Long id) {
       Character character = entityManager.find(Character.class, id);
       if(character != null) {
        entityManager.remove(character);
        return true;
       }
       return false;
    }
    
}
