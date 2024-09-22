package com.phutl.meowpet.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.phutl.meowpet.entity.PetType;
import com.phutl.meowpet.services.PetTypeService;
import com.phutl.meowpet.dto.petType.PetTypeDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${api.prefix}/petTypes")
public class PetTypeController {
    
    @Autowired
    private PetTypeService petTypeService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPetType(@ModelAttribute @Valid PetTypeDTO petTypeDTO,
           // @RequestPart("image") MultipartFile image, 
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            MultipartFile image = petTypeDTO.getImage();
            if (image.getSize() > 10 * 1024 * 1024) {
                throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Image size must be less than 10MB");
            }
            String contentType = image.getContentType();
            if (contentType == null || !contentType.contains("image")) {
                throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "File must be an image");
            }
            String fileName = storeFile(image);
            petTypeDTO.setImageUrl(fileName);
            PetType newPetType = petTypeService.createPetType(petTypeDTO);
            return ResponseEntity.ok(newPetType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Thêm UUID để đảm bảo tên file là duy nhất
        String newFileName = java.util.UUID.randomUUID().toString() + fileName;
        Path uploadDir = Paths.get("uploads");
        // Tạo thư mục nếu chưa tồn tại
        if(!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đến file
        Path destination = Paths.get(uploadDir.toString(), newFileName);
        // Copy file vào thư mục upload
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }
}
