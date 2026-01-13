package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.*;
import com.example.EcommerceBookStore.model.enums.OrderStatus;
import com.example.EcommerceBookStore.model.repositoriy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private OrderService orderService;

    private User testUser;
    private Cart testCart;
    private Books testBook;
    private CartItem testCartItem;
    private Address address;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("ion_popescu");

        testBook = new Books();
        testBook.setId(1L);
        testBook.setTitle("Clean Code");
        testBook.setPrice(120);
        testBook.setStock(10);

        testCart = new Cart();
        testCart.setUser(testUser);

        testCartItem = new CartItem();
        testCartItem.setCart(testCart);
        testCartItem.setBook(testBook);
        testCartItem.setQuantity(2);

        testCart.setItems(List.of(testCartItem));

        address = new Address();
        address.setStreet("Strada Unirii 10");
        address.setCity("Bucuresti");
        address.setCountry("Romania");
        address.setZipCode("010101");
    }

    @Test
    void testCheckout_Success() {
        Long testUserId = 1L;
        Long testAddressId = 1L;

        User testUser = new User();
        testUser.setId(testUserId);

        Cart testCart = new Cart();
        testCart.setId(1L);
        testCart.setUser(testUser);
        testCart.setItems(new ArrayList<>());

        Books testBook = new Books();
        testBook.setId(1L);
        testBook.setPrice(50);
        testBook.setStock(10);
        testBook.setTitle("Test Book");

        CartItem cartItem = new CartItem();
        cartItem.setBook(testBook);
        cartItem.setQuantity(2);
        cartItem.setCart(testCart);

        testCart.getItems().add(cartItem);

        Address address = new Address();
        address.setId(testAddressId);
        address.setCity("Bucharest");
        address.setStreet("Main Street");

        // mock repo-uri
        when(cartRepository.findByUserId(testUserId)).thenReturn(Optional.of(testCart));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(i -> i.getArguments()[0]);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        when(addressRepository.findById(testAddressId)).thenReturn(Optional.of(address));

        // apel metoda
        Order order = orderService.checkout(testUserId, testAddressId);

        // verificări
        assertNotNull(order);
        assertEquals(OrderStatus.PLACED, order.getStatus());
        assertEquals(2, order.getItems().get(0).getQuantity());
        assertEquals(8, testBook.getStock());
    }

    @Test
    void testCheckout_CartEmpty() {
        Long userId = 1L;
        Long addressId = 1L;

        User user = new User();
        user.setId(userId);

        Cart emptyCart = new Cart();
        emptyCart.setUser(user);
        emptyCart.setItems(new ArrayList<>()); // gol

        Address address = new Address();
        address.setId(addressId);

        // Mocks
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(emptyCart));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> orderService.checkout(userId, addressId));

        assertEquals("Cart is empty", ex.getMessage());
    }

    @Test
    void testCheckout_BookOutOfStock() {
        Long userId = 1L;
        Long addressId = 1L;

        User user = new User();
        user.setId(userId);

        Books testBook = new Books();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setPrice(50);
        testBook.setStock(1); // doar 1 în stoc

        Cart cart = new Cart();
        cart.setUser(user);
        CartItem cartItem = new CartItem();
        cartItem.setBook(testBook);
        cartItem.setQuantity(2); // cerem 2 -> out of stock
        cartItem.setCart(cart);
        cart.setItems(new ArrayList<>(List.of(cartItem)));

        Address address = new Address();
        address.setId(addressId);

        // Mocks
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(bookRepository.findById(testBook.getId())).thenReturn(Optional.of(testBook));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> orderService.checkout(userId, addressId));

        assertEquals("Book Test Book out of stock", ex.getMessage());
    }
}