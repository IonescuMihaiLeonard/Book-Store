package com.example.EcommerceBookStore.model.repositoriy;

import com.example.EcommerceBookStore.model.Books;
import com.example.EcommerceBookStore.model.Cart;
import com.example.EcommerceBookStore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndBookId(Long cartId, Long bookId);

    Optional<CartItem> findByCartAndBook(Cart cart, Books book);
    List<CartItem> findByCartId(Long cartId);
}
