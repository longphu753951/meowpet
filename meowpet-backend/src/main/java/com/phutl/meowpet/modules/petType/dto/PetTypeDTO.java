package com.phutl.meowpet.modules.petType.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PetTypeDTO {
    @JsonProperty("name")
    @NotBlank(message = "Name is required")
    private String name;

    @JsonProperty("description")
    @NotBlank(message = "Description is required")
    private String description;

    @JsonProperty("image")
    @NotBlank(message = "Image is required")
    private String image;
    
}
