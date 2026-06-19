package com.bookstore.order.service;

import com.bookstore.order.dto.AdminCartItemRequest;
import com.bookstore.order.dto.AdminCartRequest;
import com.bookstore.order.dto.AdminOrderItemRequest;
import com.bookstore.order.dto.AdminOrderRequest;
import com.bookstore.order.dto.AdminPaymentRequest;
import com.bookstore.order.model.Address;
import com.bookstore.order.model.Cart;
import com.bookstore.order.model.CartItem;
import com.bookstore.order.model.Order;
import com.bookstore.order.model.OrderItem;
import com.bookstore.order.model.Payment;
import com.bookstore.order.repository.AddressRepository;
import com.bookstore.order.repository.CartItemRepository;
import com.bookstore.order.repository.CartRepository;
import com.bookstore.order.repository.OrderItemRepository;
import com.bookstore.order.repository.OrderRepository;
import com.bookstore.order.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminOrderCrudService {

    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;

    public AdminOrderCrudService(
            AddressRepository addressRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            PaymentRepository paymentRepository
    ) {
        this.addressRepository = addressRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddress(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
    }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, Address updated) {
        Address address = getAddress(id);
        address.setUserId(updated.getUserId());
        address.setStreet(updated.getStreet());
        address.setCity(updated.getCity());
        address.setCountry(updated.getCountry());
        address.setZipCode(updated.getZipCode());
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new IllegalArgumentException("Address not found");
        }
        addressRepository.deleteById(id);
    }

    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
    }

    public Cart createCart(AdminCartRequest request) {
        cartRepository.findByUserId(request.userId())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Cart already exists for user");
                });
        Cart cart = new Cart();
        cart.setUserId(request.userId());
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long id, AdminCartRequest request) {
        Cart cart = getCart(id);
        cartRepository.findByUserId(request.userId())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Cart already exists for user");
                });
        cart.setUserId(request.userId());
        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new IllegalArgumentException("Cart not found");
        }
        cartRepository.deleteById(id);
    }

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem getCartItem(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
    }

    public CartItem createCartItem(AdminCartItemRequest request) {
        Cart cart = getCart(request.cartId());
        CartItem item = new CartItem();
        applyCartItem(item, cart, request);
        return cartItemRepository.save(item);
    }

    public CartItem updateCartItem(Long id, AdminCartItemRequest request) {
        CartItem item = getCartItem(id);
        Cart cart = getCart(request.cartId());
        applyCartItem(item, cart, request);
        return cartItemRepository.save(item);
    }

    public void deleteCartItem(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new IllegalArgumentException("Cart item not found");
        }
        cartItemRepository.deleteById(id);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public Order createOrder(AdminOrderRequest request) {
        Address address = getAddress(request.addressId());
        Order order = new Order();
        order.setUserId(request.userId());
        order.setShippingAddress(address);
        order.setStatus(request.status());
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, AdminOrderRequest request) {
        Order order = getOrder(id);
        Address address = getAddress(request.addressId());
        order.setUserId(request.userId());
        order.setShippingAddress(address);
        order.setStatus(request.status());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found");
        }
        paymentRepository.deleteAll(paymentRepository.findByOrderId(id));
        orderItemRepository.deleteAll(orderItemRepository.findByOrderId(id));
        orderRepository.deleteById(id);
    }

    public List<OrderItem> getOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItem(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order item not found"));
    }

    public OrderItem createOrderItem(AdminOrderItemRequest request) {
        Order order = getOrder(request.orderId());
        OrderItem item = new OrderItem();
        applyOrderItem(item, order, request);
        return orderItemRepository.save(item);
    }

    public OrderItem updateOrderItem(Long id, AdminOrderItemRequest request) {
        OrderItem item = getOrderItem(id);
        Order order = getOrder(request.orderId());
        applyOrderItem(item, order, request);
        return orderItemRepository.save(item);
    }

    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new IllegalArgumentException("Order item not found");
        }
        orderItemRepository.deleteById(id);
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
    }

    public Payment createPayment(AdminPaymentRequest request) {
        Order order = getOrder(request.orderId());
        Payment payment = new Payment();
        applyPayment(payment, order, request);
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, AdminPaymentRequest request) {
        Payment payment = getPayment(id);
        Order order = getOrder(request.orderId());
        applyPayment(payment, order, request);
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalArgumentException("Payment not found");
        }
        paymentRepository.deleteById(id);
    }

    private void applyCartItem(CartItem item, Cart cart, AdminCartItemRequest request) {
        item.setCart(cart);
        item.setBookId(request.bookId());
        item.setBookTitle(request.bookTitle());
        item.setUnitPrice(request.unitPrice());
        item.setQuantity(request.quantity());
    }

    private void applyOrderItem(OrderItem item, Order order, AdminOrderItemRequest request) {
        item.setOrder(order);
        item.setBookId(request.bookId());
        item.setBookTitle(request.bookTitle());
        item.setPrice(request.price());
        item.setQuantity(request.quantity());
    }

    private void applyPayment(Payment payment, Order order, AdminPaymentRequest request) {
        payment.setOrder(order);
        payment.setStatus(request.status());
        payment.setAmount(request.amount());
        payment.setPaidAt(request.paidAt() != null ? request.paidAt() : LocalDateTime.now());
    }
}
