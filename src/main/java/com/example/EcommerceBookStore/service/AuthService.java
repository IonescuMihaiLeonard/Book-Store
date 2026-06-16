package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.User;
import com.example.EcommerceBookStore.config.JwtUtil;
import com.example.EcommerceBookStore.model.enums.Role;
import com.example.EcommerceBookStore.model.repositoriy.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Înregistrare
    public User register(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) throw new RuntimeException("Username already exists");
        if (userRepository.existsByEmail(email)) throw new RuntimeException("Email already exists");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.CUSTOMER);

        return userRepository.save(user);
    }

    // Login
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getUsername(), String.valueOf(user.getRole()));
    }
}
