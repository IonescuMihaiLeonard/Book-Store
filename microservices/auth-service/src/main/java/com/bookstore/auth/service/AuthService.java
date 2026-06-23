package com.bookstore.auth.service;

import com.bookstore.auth.config.JwtUtil;
import com.bookstore.auth.dto.AdminUserRequest;
import com.bookstore.auth.dto.AuthResponse;
import com.bookstore.auth.dto.RegisterRequest;
import com.bookstore.auth.dto.UserResponse;
import com.bookstore.auth.model.Role;
import com.bookstore.auth.model.User;
import com.bookstore.auth.repository.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse register(RegisterRequest request) {
        log.info("Register request received for username={}", request.username());
        if (userRepository.existsByUsername(request.username())) {
            log.warn("Register rejected because username already exists: {}", request.username());
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            log.warn("Register rejected because email already exists for username={}", request.username());
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.CUSTOMER);

        User savedUser = userRepository.save(user);
        log.info("User registered successfully with id={} and role={}", savedUser.getId(), savedUser.getRole());
        return toResponse(savedUser);
    }

    public AuthResponse login(String usernameOrEmail, String password) {
        log.info("Login attempt received");
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> {
                    log.warn("Login rejected because account was not found");
                    return new IllegalArgumentException("Invalid credentials");
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Login rejected for userId={} because password did not match", user.getId());
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        log.info("Login successful for userId={} role={}", user.getId(), user.getRole());
        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getRole().name());
    }

    public UserResponse getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toResponse);
    }

    public UserResponse getUser(Long id) {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public UserResponse createUser(AdminUserRequest request) {
        log.info("Admin create user request for username={} role={}", request.username(), request.role());
        if (userRepository.existsByUsername(request.username())) {
            log.warn("Admin create user rejected because username already exists: {}", request.username());
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            log.warn("Admin create user rejected because email already exists for username={}", request.username());
            throw new IllegalArgumentException("Email already exists");
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        User savedUser = userRepository.save(user);
        log.info("Admin created user id={} role={}", savedUser.getId(), savedUser.getRole());
        return toResponse(savedUser);
    }

    public UserResponse updateUser(Long id, AdminUserRequest request) {
        log.info("Admin update user request for id={}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.findByUsername(request.username())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Username already exists");
                });
        if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setUsername(request.username());
        user.setEmail(request.email());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        user.setRole(request.role());
        User savedUser = userRepository.save(user);
        log.info("Admin updated user id={} role={}", savedUser.getId(), savedUser.getRole());
        return toResponse(savedUser);
    }

    public void deleteUser(Long id) {
        log.info("Admin delete user request for id={}", id);
        if (!userRepository.existsById(id)) {
            log.warn("Admin delete user rejected because user id={} was not found", id);
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
        log.info("Admin deleted user id={}", id);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }
}
