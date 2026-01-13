package com.example.EcommerceBookStore.model.repositoriy;

import com.example.EcommerceBookStore.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBookId(Long bookId);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
