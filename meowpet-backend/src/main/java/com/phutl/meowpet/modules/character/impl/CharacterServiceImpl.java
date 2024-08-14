package com.phutl.meowpet.modules.character.impl;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.phutl.meowpet.modules.character.CharacterRepository;
import com.phutl.meowpet.modules.character.CharacterService;
import com.phutl.meowpet.modules.character.dto.CreateCharacterDTO;
import com.phutl.meowpet.modules.database.Character;
import java.io.IOException;
import com.opencsv.exceptions.CsvException;
import jakarta.transaction.Transactional;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    

    @Override
    public Character createCharacter(CreateCharacterDTO createCharacterDTO) throws Exception {
        
            Optional<Character> existingCharacter = getCharacterByName(createCharacterDTO.getName());
            if(existingCharacter.isPresent()) {
                throw new Exception("Character already exists");
            }
            Character newCharacter = Character.builder()
                    .name(createCharacterDTO.getName())
                    .description(createCharacterDTO.getDescription())
                    .build();
            characterRepository.save(newCharacter);
            return newCharacter;
       
    }

    @Override
    public void updateCharacter(Long id, CreateCharacterDTO createCharacterDTO) {
        // TODO Auto-generated method stub
    }

    @Override
    @Transactional
    public String importCharacterFromCsv(MultipartFile file) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> records = reader.readAll();
            List<Character> characters = new ArrayList<>();
            for (String[] record: records) {
                Character character = Character.builder()
                        .name(record[0])
                        .description(record[1])
                        .build();
                characters.add(character);
            }
            characterRepository.saveAll(characters);
            return "Import success";
        }  
    }

    @Override
    public Optional<Character> getCharacter(Long id) {
        return characterRepository.findById(id);

    }

    @Override
    public Optional<Character> getCharacterByName(String name) {
        return characterRepository.findByName(name);
    }

    @Override
    public boolean softDeleteCharacter(Long id) {
        return characterRepository.softDelete(id);
    }

    @Override
    public boolean hardDeleteCharacter(Long id) {
        return characterRepository.hardDelete(id);
    }
    
}
