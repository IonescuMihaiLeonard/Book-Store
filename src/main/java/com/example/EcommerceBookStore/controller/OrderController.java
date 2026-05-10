package com.example.EcommerceBookStore.controller;

import com.example.EcommerceBookStore.model.Order;
import com.example.EcommerceBookStore.model.enums.OrderStatus;
import com.example.EcommerceBookStore.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> myOrders() {
        return orderService.getMyOrders();
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return orderService.updateStatus(id, status);
    }
    @PostMapping("/checkout")
    public Order checkout(@RequestParam Long addressId) {
        return orderService.checkout(orderService.getCurrentUser().getId(), addressId);
    }
}
