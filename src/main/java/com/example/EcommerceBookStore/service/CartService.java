package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.*;
import com.example.EcommerceBookStore.model.repositoriy.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       BookRepository bookRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    // ---------------- View cart ----------------
    public Cart getCart() {
        User user = getCurrentUser();
        return getOrCreateCart(user);
    }

    // ---------------- Add to cart ----------------
    @Transactional
    public Cart addToCart(Long bookId, int quantity) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        CartItem item = cartItemRepository.findByCartAndBook(cart, book)
                .orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setBook(book);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }

        cartItemRepository.save(item);
        return cart;
    }

    // ---------------- Update quantity ----------------
    @Transactional
    public Cart updateQuantity(Long itemId, int quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return item.getCart();
    }

    // ---------------- Remove item ----------------
    @Transactional
    public Cart removeItem(Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        System.out.println("Found item: " + item.getId() + ", cart: " + (item.getCart() != null ? item.getCart().getId() : "null"));
        Cart cart = item.getCart();
        cartItemRepository.delete(item);
        return cart;
    }
}