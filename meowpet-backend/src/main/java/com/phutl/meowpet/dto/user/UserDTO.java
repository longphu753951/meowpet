package com.phutl.meowpet.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phutl.meowpet.shared.common.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDTO {
    
    @JsonProperty("first_name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @JsonProperty("email")
    @NotBlank(message = "Email is required")
    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @JsonProperty("retype_password")
    private String retypePassword;

    @NotBlank(message = "Password is required")
    private String password;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    private Set<Role> roles;
}
