package com.example.EcommerceBookStore.model.repositoriy;

import com.example.EcommerceBookStore.model.Cart;
import com.example.EcommerceBookStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);

    Optional<Cart> findByUser(User user);
}
