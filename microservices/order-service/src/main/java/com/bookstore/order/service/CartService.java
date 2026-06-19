package com.bookstore.order.service;

import com.bookstore.order.dto.AddCartItemRequest;
import com.bookstore.order.dto.UpdateCartItemRequest;
import com.bookstore.order.model.Cart;
import com.bookstore.order.model.CartItem;
import com.bookstore.order.repository.CartItemRepository;
import com.bookstore.order.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart getCart(Long userId) {
        return getOrCreateCart(userId);
    }

    @Transactional
    public Cart addToCart(Long userId, AddCartItemRequest request) {
        Cart cart = getOrCreateCart(userId);

        CartItem item = cartItemRepository.findByCartAndBookId(cart, request.bookId())
                .orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setBookId(request.bookId());
            item.setBookTitle(request.bookTitle());
            item.setUnitPrice(request.unitPrice());
            item.setQuantity(request.quantity());
            cart.getItems().add(item);
        } else {
            item.setBookTitle(request.bookTitle());
            item.setUnitPrice(request.unitPrice());
            item.setQuantity(item.getQuantity() + request.quantity());
        }

        cartItemRepository.save(item);
        return cart;
    }

    @Transactional
    public Cart updateQuantity(Long userId, Long itemId, UpdateCartItemRequest request) {
        CartItem item = getUserCartItem(userId, itemId);
        item.setQuantity(request.quantity());
        cartItemRepository.save(item);
        return item.getCart();
    }

    @Transactional
    public Cart removeItem(Long userId, Long itemId) {
        CartItem item = getUserCartItem(userId, itemId);
        Cart cart = item.getCart();
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        return cart;
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    return cartRepository.save(cart);
                });
    }

    private CartItem getUserCartItem(Long userId, Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        if (!item.getCart().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized cart item access");
        }
        return item;
    }
}
