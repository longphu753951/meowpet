package com.phutl.meowpet.shared.responses.user;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

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
    private String username;
    private String token;
    private String refreshToken;
    private Collection<? extends GrantedAuthority> authorities;
}
