package com.phutl.meowpet.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phutl.meowpet.dto.user.UserDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDTO extends UserDTO {
    @JsonProperty("address")
    @NotBlank(message = "Address is required")
    private String address;
    
    @JsonProperty("gender")
    @NotBlank(message = "gender is required")
    private String gender;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;
}
