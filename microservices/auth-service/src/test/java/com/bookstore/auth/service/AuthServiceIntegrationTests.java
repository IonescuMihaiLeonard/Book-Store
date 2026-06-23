package com.bookstore.auth.service;

import com.bookstore.auth.dto.AuthResponse;
import com.bookstore.auth.dto.RegisterRequest;
import com.bookstore.auth.dto.UserResponse;
import com.bookstore.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceIntegrationTests {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }

    @Test
    void registerCreatesCustomerAndLoginReturnsJwtToken() {
        UserResponse user = authService.register(new RegisterRequest(
                "customer1",
                "customer1@bookstore.test",
                "pass123"
        ));

        AuthResponse login = authService.login("customer1", "pass123");

        assertThat(user.id()).isNotNull();
        assertThat(user.role()).isEqualTo("CUSTOMER");
        assertThat(login.token()).isNotBlank();
        assertThat(login.username()).isEqualTo("customer1");
        assertThat(login.role()).isEqualTo("CUSTOMER");
    }

    @Test
    void registerRejectsDuplicateUsername() {
        authService.register(new RegisterRequest(
                "duplicate",
                "duplicate@bookstore.test",
                "pass123"
        ));

        assertThatThrownBy(() -> authService.register(new RegisterRequest(
                "duplicate",
                "other@bookstore.test",
                "pass123"
        )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username already exists");
    }

    @Test
    void loginRejectsInvalidPassword() {
        authService.register(new RegisterRequest(
                "loginuser",
                "loginuser@bookstore.test",
                "good-password"
        ));

        assertThatThrownBy(() -> authService.login("loginuser", "bad-password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid credentials");
    }
}
