package com.example.demo.repository;

import com.example.demo.model.AppUser;

import java.util.Optional;

public interface AppUserRepository {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    AppUser save(AppUser user);
}
