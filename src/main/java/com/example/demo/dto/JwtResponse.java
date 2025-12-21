package com.example.demo.dto;

public class JwtResponse {

    private String token;
    private String tokenType = "Bearer";
    private String email;
    private String role;

    public JwtResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
