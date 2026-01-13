package com.example.EcommerceBookStore.model.repositoriy;

import com.example.EcommerceBookStore.model.Order;
import com.example.EcommerceBookStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findByUser(User user);
}
