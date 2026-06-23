package com.bookstore.order.service;

import com.bookstore.order.dto.AddCartItemRequest;
import com.bookstore.order.model.Address;
import com.bookstore.order.model.OrderStatus;
import com.bookstore.order.model.PaymentStatus;
import com.bookstore.order.repository.AddressRepository;
import com.bookstore.order.repository.CartItemRepository;
import com.bookstore.order.repository.CartRepository;
import com.bookstore.order.repository.OrderItemRepository;
import com.bookstore.order.repository.OrderRepository;
import com.bookstore.order.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class OrderFlowIntegrationTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    void cleanDatabase() {
        paymentRepository.deleteAll();
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        cartItemRepository.deleteAll();
        cartRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void checkoutCreatesOrderPaymentAndClearsCart() {
        Long userId = 10L;
        cartService.addToCart(userId, new AddCartItemRequest(
                100L,
                "Domain-Driven Design",
                120.00,
                2
        ));

        Address address = new Address();
        address.setStreet("Strada Test 1");
        address.setCity("Bucuresti");
        address.setCountry("Romania");
        address.setZipCode("010101");
        address = addressService.addAddress(userId, address);

        var order = orderService.checkout(userId, address.getId());

        assertThat(order.getId()).isNotNull();
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PLACED);
        assertThat(order.getItems()).hasSize(1);
        assertThat(order.getItems().getFirst().getBookTitle()).isEqualTo("Domain-Driven Design");
        assertThat(order.getItems().getFirst().getQuantity()).isEqualTo(2);
        var cart = cartRepository.findByUserId(userId).orElseThrow();
        assertThat(cartItemRepository.countByCart(cart)).isZero();

        var payments = paymentRepository.findAll();
        assertThat(payments).hasSize(1);
        assertThat(payments.getFirst().getStatus()).isEqualTo(PaymentStatus.SUCCESS);
        assertThat(payments.getFirst().getAmount()).isEqualTo(240.00);
    }

    @Test
    void checkoutRejectsEmptyCart() {
        Long userId = 20L;

        Address address = new Address();
        address.setStreet("Strada Test 2");
        address.setCity("Cluj");
        address.setCountry("Romania");
        address.setZipCode("400000");
        address = addressService.addAddress(userId, address);
        Long addressId = address.getId();

        assertThatThrownBy(() -> orderService.checkout(userId, addressId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cart not found");
    }

    @Test
    void checkoutRejectsAddressOwnedByAnotherUser() {
        Long cartOwnerId = 30L;
        Long addressOwnerId = 31L;

        cartService.addToCart(cartOwnerId, new AddCartItemRequest(
                200L,
                "Refactoring",
                90.00,
                1
        ));

        Address address = new Address();
        address.setStreet("Strada Altuia 3");
        address.setCity("Iasi");
        address.setCountry("Romania");
        address.setZipCode("700000");
        address = addressService.addAddress(addressOwnerId, address);

        Long addressId = address.getId();
        assertThatThrownBy(() -> orderService.checkout(cartOwnerId, addressId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unauthorized address access");
    }
}
