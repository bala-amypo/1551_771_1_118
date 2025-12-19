package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.AppUser;

public interface AuthService {

    void register(RegisterRequest request);

    AppUser login(LoginRequest request);
}
