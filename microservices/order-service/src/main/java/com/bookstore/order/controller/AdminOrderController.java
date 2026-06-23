package com.bookstore.order.controller;

import com.bookstore.order.dto.UpdateOrderStatusRequest;
import com.bookstore.order.model.Order;
import com.bookstore.order.model.OrderStatus;
import com.bookstore.order.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getOrders(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable,
            @RequestParam MultiValueMap<String, String> params
    ) {
        if (hasPagingOrSorting(params)) {
            return ResponseEntity.ok(orderService.getAllOrders(pageable));
        }
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long id,
            @RequestParam(required = false) OrderStatus status,
            @RequestBody(required = false) UpdateOrderStatusRequest request
    ) {
        OrderStatus resolvedStatus = status != null ? status : request != null ? request.status() : null;
        if (resolvedStatus == null) {
            throw new IllegalArgumentException("Order status is required");
        }
        return ResponseEntity.ok(orderService.updateStatus(id, resolvedStatus));
    }

    private boolean hasPagingOrSorting(MultiValueMap<String, String> params) {
        return params.containsKey("page") || params.containsKey("size") || params.containsKey("sort");
    }
}
