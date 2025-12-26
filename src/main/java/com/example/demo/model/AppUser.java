package com.example.demo.model;

public class AppUser {

    private Long id;
    private String email;
    private Role role;

    public AppUser() {}

    public AppUser(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
