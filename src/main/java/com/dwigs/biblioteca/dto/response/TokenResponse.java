package com.dwigs.biblioteca.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TokenResponse {
    private String token;
    private String tokenType = "Bearer";
    private String[] rolKeys;

    public TokenResponse(String token, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;

        List<String> authorityStrings = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        this.rolKeys = authorityStrings.toArray(new String[0]);
    }
}
