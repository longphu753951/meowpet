package com.phutl.meowpet.modules.character.dto;

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
@Getter
@Setter
public class CreateCharacterDTO {
    @JsonProperty("name")
    @NotBlank(message = "Name is required")
    private String name;

    @JsonProperty("description")
    private String description;
}
