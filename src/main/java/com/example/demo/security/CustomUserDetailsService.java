package com.example.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Dummy in-memory user (safe for tests)
        if (!"user".equals(username)) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(
                "user",
                "{noop}password", // {noop} avoids password encoder issues
                Collections.emptyList()
        );
    }
}
