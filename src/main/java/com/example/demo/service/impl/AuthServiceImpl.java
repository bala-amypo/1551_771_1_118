package com.example.demo.service.impl;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AppUserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser registerUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
