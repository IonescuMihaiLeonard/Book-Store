package com.bookstore.order.controller;

import com.bookstore.order.dto.AddCartItemRequest;
import com.bookstore.order.dto.UpdateCartItemRequest;
import com.bookstore.order.model.Cart;
import com.bookstore.order.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addItem(
            @RequestParam Long userId,
            @Valid @RequestBody AddCartItemRequest request
    ) {
        return ResponseEntity.ok(cartService.addToCart(userId, request));
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Cart> updateItem(
            @RequestParam Long userId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {
        return ResponseEntity.ok(cartService.updateQuantity(userId, id, request));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Cart> removeItem(@RequestParam Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(cartService.removeItem(userId, id));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
