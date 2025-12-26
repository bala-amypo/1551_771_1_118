package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider provider;

    public AuthController(AppUserRepository repo,
                          PasswordEncoder encoder,
                          JwtTokenProvider provider) {
        this.repo = repo;
        this.encoder = encoder;
        this.provider = provider;
    }

    @PostMapping("/register")
    public AppUser register(@RequestBody RegisterRequest req) {
        AppUser user = new AppUser();
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(req.getRole());
        return repo.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        AppUser user = repo.findByEmail(req.getEmail()).orElseThrow();
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return provider.generateToken(user);
    }
}
