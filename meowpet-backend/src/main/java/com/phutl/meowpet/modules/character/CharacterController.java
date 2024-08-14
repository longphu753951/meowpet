package com.phutl.meowpet.modules.character;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phutl.meowpet.modules.character.dto.CreateCharacterDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("${api.prefix}/characters")
public class CharacterController {
    @Autowired
    private CharacterService characterService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewCharacter(@Valid @RequestBody CreateCharacterDTO createCharacterDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(error -> error.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            characterService.createCharacter(createCharacterDTO);
            return ResponseEntity.ok("Character created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/import")
    public ResponseEntity<?> postMethodName(@RequestParam("file") MultipartFile file) {
        try {
            characterService.importCharacterFromCsv(file);
            return ResponseEntity.ok("Import success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id) {
        boolean isDeleted = characterService.softDeleteCharacter(id);
        if (isDeleted) {
            return ResponseEntity.ok("User soft deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<?> hardDelete(@PathVariable Long id) {
        boolean isDeleted = characterService.hardDeleteCharacter(id);
        if(isDeleted) {
            return ResponseEntity.ok("Character hard deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}
