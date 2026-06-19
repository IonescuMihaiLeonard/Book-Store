package com.bookstore.order.service;

import com.bookstore.order.model.Address;
import com.bookstore.order.model.Cart;
import com.bookstore.order.model.CartItem;
import com.bookstore.order.model.Order;
import com.bookstore.order.model.OrderItem;
import com.bookstore.order.model.OrderStatus;
import com.bookstore.order.model.Payment;
import com.bookstore.order.model.PaymentStatus;
import com.bookstore.order.repository.AddressRepository;
import com.bookstore.order.repository.CartRepository;
import com.bookstore.order.repository.OrderItemRepository;
import com.bookstore.order.repository.OrderRepository;
import com.bookstore.order.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final AddressRepository addressRepository;

    public OrderService(
            CartRepository cartRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            PaymentRepository paymentRepository,
            AddressRepository addressRepository
    ) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Order checkout(Long userId, Long addressId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        if (!address.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized address access");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.PLACED);
        order.setShippingAddress(address);
        order.setCreatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        double total = 0;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBookId(cartItem.getBookId());
            orderItem.setBookTitle(cartItem.getBookTitle());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getUnitPrice());
            orderItemRepository.save(orderItem);
            order.getItems().add(orderItem);
            total += cartItem.getUnitPrice() * cartItem.getQuantity();
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(total);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);

        cart.getItems().clear();
        cartRepository.save(cart);

        return order;
    }

    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
