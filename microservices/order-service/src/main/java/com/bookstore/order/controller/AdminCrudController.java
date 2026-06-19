package com.bookstore.order.controller;

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
import com.bookstore.order.service.AdminOrderCrudService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminCrudController {

    private final AdminOrderCrudService adminService;

    public AdminCrudController(AdminOrderCrudService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAddresses() {
        return ResponseEntity.ok(adminService.getAddresses());
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAddress(id));
    }

    @PostMapping("/addresses")
    public ResponseEntity<Address> createAddress(@Valid @RequestBody Address address) {
        return ResponseEntity.ok(adminService.createAddress(address));
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @Valid @RequestBody Address address) {
        return ResponseEntity.ok(adminService.updateAddress(id, address));
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        adminService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(adminService.getCarts());
    }

    @GetMapping("/carts/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getCart(id));
    }

    @PostMapping("/carts")
    public ResponseEntity<Cart> createCart(@Valid @RequestBody AdminCartRequest request) {
        return ResponseEntity.ok(adminService.createCart(request));
    }

    @PutMapping("/carts/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @Valid @RequestBody AdminCartRequest request) {
        return ResponseEntity.ok(adminService.updateCart(id, request));
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        adminService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItem>> getCartItems() {
        return ResponseEntity.ok(adminService.getCartItems());
    }

    @GetMapping("/cart-items/{id}")
    public ResponseEntity<CartItem> getCartItem(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getCartItem(id));
    }

    @PostMapping("/cart-items")
    public ResponseEntity<CartItem> createCartItem(@Valid @RequestBody AdminCartItemRequest request) {
        return ResponseEntity.ok(adminService.createCartItem(request));
    }

    @PutMapping("/cart-items/{id}")
    public ResponseEntity<CartItem> updateCartItem(
            @PathVariable Long id,
            @Valid @RequestBody AdminCartItemRequest request
    ) {
        return ResponseEntity.ok(adminService.updateCartItem(id, request));
    }

    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        adminService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getOrder(id));
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody AdminOrderRequest request) {
        return ResponseEntity.ok(adminService.createOrder(request));
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody AdminOrderRequest request) {
        return ResponseEntity.ok(adminService.updateOrder(id, request));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        adminService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/order-items")
    public ResponseEntity<List<OrderItem>> getOrderItems() {
        return ResponseEntity.ok(adminService.getOrderItems());
    }

    @GetMapping("/order-items/{id}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getOrderItem(id));
    }

    @PostMapping("/order-items")
    public ResponseEntity<OrderItem> createOrderItem(@Valid @RequestBody AdminOrderItemRequest request) {
        return ResponseEntity.ok(adminService.createOrderItem(request));
    }

    @PutMapping("/order-items/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(
            @PathVariable Long id,
            @Valid @RequestBody AdminOrderItemRequest request
    ) {
        return ResponseEntity.ok(adminService.updateOrderItem(id, request));
    }

    @DeleteMapping("/order-items/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        adminService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getPayments() {
        return ResponseEntity.ok(adminService.getPayments());
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getPayment(id));
    }

    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody AdminPaymentRequest request) {
        return ResponseEntity.ok(adminService.createPayment(request));
    }

    @PutMapping("/payments/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @Valid @RequestBody AdminPaymentRequest request) {
        return ResponseEntity.ok(adminService.updatePayment(id, request));
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        adminService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
