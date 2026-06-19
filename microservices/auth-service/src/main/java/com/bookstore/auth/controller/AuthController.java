package com.bookstore.auth.controller;

import com.bookstore.auth.config.JwtUtil;
import com.bookstore.auth.dto.AuthResponse;
import com.bookstore.auth.dto.LoginRequest;
import com.bookstore.auth.dto.RegisterRequest;
import com.bookstore.auth.dto.UserResponse;
import com.bookstore.auth.service.AuthService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request.username(), request.password());
    }

    @GetMapping("/validate")
    public Map<String, Object> validate(@RequestParam String token) {
        boolean valid = jwtUtil.validateToken(token);
        return Map.of(
                "valid", valid,
                "username", jwtUtil.extractUsername(token),
                "role", jwtUtil.extractRole(token)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(authService.getByUsername(jwtUtil.extractUsername(token)));
    }
}
