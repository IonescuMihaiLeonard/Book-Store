package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.*;
import com.example.EcommerceBookStore.model.enums.OrderStatus;
import com.example.EcommerceBookStore.model.enums.PaymentStatus;
import com.example.EcommerceBookStore.model.repositoriy.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;
    private final PaymentRepository paymentRepository;
    private final AddressRepository addressRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    public OrderService(CartRepository cartRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, BookRepository bookRepository, PaymentRepository paymentRepository, AddressRepository addressRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.bookRepository = bookRepository;
        this.paymentRepository = paymentRepository;
        this.addressRepository = addressRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public Order checkout(Long userId, Long addressId) {

        // 1️⃣ preia coșul
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // 2️⃣ creează comanda
        Order order = new Order();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);
        order.setShippingAddress(address);
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        // 3️⃣ creează OrderItems și actualizează stocul
        for (CartItem cartItem : cart.getItems()) {
            Books book = bookRepository.findById(cartItem.getBook().getId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            if (book.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Book " + book.getTitle() + " out of stock");
            }

            book.setStock(book.getStock() - cartItem.getQuantity());
            bookRepository.save(book);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(book);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(book.getPrice());
            orderItemRepository.save(orderItem);

            order.getItems().add(orderItem);
        }

        // 4️⃣ simulare plată (mock)
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(cart.getItems().stream()
                .mapToDouble(i -> i.getBook().getPrice() * i.getQuantity())
                .sum());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);

        // 5️⃣ golește coșul
        cart.getItems().clear();

        return order;
    }

    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    public List<Order> getMyOrders() {
        User user = getCurrentUser();
        return orderRepository.findByUser(user);
    }

    public Order updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }
}