package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.*;
import com.example.EcommerceBookStore.model.repositoriy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    private User testUser;
    private Books testBook;
    private Cart testCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("ion_popescu");

        testBook = new Books();
        testBook.setId(1L);
        testBook.setTitle("Clean Code");
        testBook.setStock(10);
        testBook.setPrice(120);

        testCart = new Cart();
        testCart.setId(1L);
        testCart.setUser(testUser);
    }

    @Test
    void testAddToCart_NewItem() {
        testUser.setUsername("ion_popescu");
        testUser.setId(1L);

        // pregătire cart
        testCart = new Cart();
        testCart.setUser(testUser);
        testCart.setItems(new ArrayList<>());

        // pregătire book
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setStock(10);

        // mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(testUser.getUsername());

        // mock SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // mock repo-uri
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUser(testUser)).thenReturn(Optional.of(testCart));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(cartItemRepository.findByCartAndBook(testCart, testBook)).thenReturn(Optional.empty());
        when(cartItemRepository.save(any(CartItem.class))).thenAnswer(i -> i.getArguments()[0]);

        // apelăm metoda
        Cart result = cartService.addToCart(1L, 2);

        // verificări
        assertEquals(1, result.getItems().size());
        assertEquals(2, result.getItems().get(0).getQuantity());
    }
}
