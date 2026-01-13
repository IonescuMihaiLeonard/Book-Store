package com.example.EcommerceBookStore.model.repositoriy;

import com.example.EcommerceBookStore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
