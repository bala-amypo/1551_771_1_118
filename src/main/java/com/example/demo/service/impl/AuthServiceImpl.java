package com.example.demo.service.impl;

import com.example.demo.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    // simple in-memory store
    private final Map<String, String> users = new HashMap<>();

    @Override
    public String register(String username, String password) {
        if (users.containsKey(username)) {
            return "User already exists";
        }

        users.put(username, password);
        return "User registered successfully";
    }

    @Override
    public String login(String username, String password) {
        if (!users.containsKey(username)) {
            return "User not found";
        }

        if (!users.get(username).equals(password)) {
            return "Invalid password";
        }

        return "Login successful";
    }
}
