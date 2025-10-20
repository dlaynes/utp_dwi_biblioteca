package com.dwigs.biblioteca.dto.response;

public class TokenResponse {
    private String token;
    private String tokenType = "Bearer";

    public TokenResponse(String token) { this.token = token; }
}
