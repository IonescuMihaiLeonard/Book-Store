package com.example.EcommerceBookStore.controller;

import com.example.EcommerceBookStore.model.Cart;
import com.example.EcommerceBookStore.service.CartService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // View cart
    @GetMapping
    public Cart getCart() {
        return cartService.getCart();
    }

    // Add item
    @PostMapping("/items")
    public Cart addItem(@RequestBody AddItemRequest request) {
        return cartService.addToCart(request.getBookId(), request.getQuantity());
    }

    // Update quantity
    @PutMapping("/items/{id}")
    public Cart update(@PathVariable Long id, @RequestBody UpdateItemRequest request) {
        return cartService.updateQuantity(id, request.getQuantity());
    }

    // Delete item
    @DeleteMapping("/items/{id}")
    public Cart delete(@PathVariable Long id) {
        return cartService.removeItem(id);
    }

    // DTOs
    @Data
    static class AddItemRequest {
        private Long bookId;
        private int quantity;
    }

    @Data
    static class UpdateItemRequest {
        private int quantity;
    }
}
