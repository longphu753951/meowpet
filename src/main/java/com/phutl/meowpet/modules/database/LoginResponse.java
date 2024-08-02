package com.phutl.meowpet.modules.database;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class LoginResponse {
    private String message;
    private User user;
    private String token;
    
}
