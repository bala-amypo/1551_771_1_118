package com.example.demo.dto;

import com.example.demo.model.Role;

public class RegisterRequest {

    private String email;
    private String password;
    private Role role;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
