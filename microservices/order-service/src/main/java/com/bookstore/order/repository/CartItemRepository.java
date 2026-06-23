package com.bookstore.order.repository;

import com.bookstore.order.model.Cart;
import com.bookstore.order.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndBookId(Cart cart, Long bookId);

    long countByCart(Cart cart);
}
