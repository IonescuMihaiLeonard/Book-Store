package com.example.EcommerceBookStore.service;


import com.example.EcommerceBookStore.config.JwtUtil;
import com.example.EcommerceBookStore.model.User;
import com.example.EcommerceBookStore.model.repositoriy.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private User user;
    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("ion_popescu");
        user.setEmail("ion.popescu@gmail.com");
        user.setPassword("parola123");
    }

    @Test
    void testRegister_Success() {
        String username = "ion_popescu";
        String email = "ion.popescu@gmail.com";
        String rawPassword = "parola123";

        // mock pentru repo și password encoder
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn("hashed123");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // apel corect
        User saved = authService.register(username, email, rawPassword);

        // verificări
        assertNotNull(saved);
        assertEquals(username, saved.getUsername());
        assertEquals(email, saved.getEmail());
        assertEquals("hashed123", saved.getPassword());
    }

    @Test
    void testRegister_UserExists() {
        String username = "ion_popescu";
        String email = "ion.popescu@gmail.com";
        String password = "parola123";

        // mock repo pentru user existent
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // apelul trebuie să arunce RuntimeException
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                authService.register(username, email, password)
        );

        assertEquals("Username already exists", ex.getMessage());
    }

    @Test
    void testLogin_Success() {
        User testUser = new User();
        testUser.setUsername("ion_popescu");
        testUser.setPassword("hashed123");

        when(userRepository.findByUsername("ion_popescu")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("parola123", "hashed123")).thenReturn(true);
        when(jwtUtil.generateToken(testUser.getUsername())).thenReturn("mocked-jwt-token"); // <-- aici

        String token = authService.login("ion_popescu", "parola123");

        assertNotNull(token);
        assertEquals("mocked-jwt-token", token);
    }

    @Test
    void testLogin_InvalidCredentials() {
        String username = "ion_popescu";
        String password = "wrongpass";

        User user = new User();
        user.setUsername(username);
        user.setPassword("hashed123");

        // Mocks
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, "hashed123")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.login(username, password));

        assertEquals("Invalid password", ex.getMessage());
    }
}