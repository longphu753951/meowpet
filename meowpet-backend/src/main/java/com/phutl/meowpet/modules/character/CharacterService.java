package com.phutl.meowpet.modules.character;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import com.opencsv.exceptions.CsvException;
import com.phutl.meowpet.modules.character.dto.CreateCharacterDTO;
import com.phutl.meowpet.modules.database.Character;

public interface CharacterService {
    Optional<Character> getCharacter(Long id);
    Optional<Character> getCharacterByName(String name);
    boolean softDeleteCharacter(Long id);
    boolean hardDeleteCharacter(Long id);
    Character createCharacter(CreateCharacterDTO createCharacterDTO) throws Exception;
    void updateCharacter(Long id, CreateCharacterDTO createCharacterDTO);
    String importCharacterFromCsv(MultipartFile file) throws IOException, CsvException;
}
