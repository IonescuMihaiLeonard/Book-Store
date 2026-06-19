package com.bookstore.order.controller;

import com.bookstore.order.dto.CheckoutRequest;
import com.bookstore.order.model.Order;
import com.bookstore.order.model.OrderStatus;
import com.bookstore.order.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getOrdersForUser(userId));
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(
            @RequestParam Long userId,
            @Valid @RequestBody CheckoutRequest request
    ) {
        return ResponseEntity.ok(orderService.checkout(userId, request.addressId()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }
}
