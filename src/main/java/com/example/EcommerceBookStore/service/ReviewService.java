package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.Books;
import com.example.EcommerceBookStore.model.Review;
import com.example.EcommerceBookStore.model.User;
import com.example.EcommerceBookStore.model.repositoriy.BookRepository;
import com.example.EcommerceBookStore.model.repositoriy.OrderRepository;
import com.example.EcommerceBookStore.model.repositoriy.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         BookRepository bookRepository,
                         OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
    }

    // Adaugă review pentru o carte
    public Review addReview(Long userId, Long bookId, int rating, String comment) {

        // 1️⃣ Verifică dacă userul a cumpărat cartea
        boolean purchased = orderRepository.findByUserId(userId).stream()
                .flatMap(order -> order.getItems().stream())
                .anyMatch(item -> item.getBook().getId().equals(bookId));

        if (!purchased) {
            throw new RuntimeException("User must purchase the book before leaving a review");
        }

        // 2️⃣ Creează review-ul
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Review review = new Review();
        review.setBook(book);
        User user = new User();
        user.setId(userId);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    // Obține toate review-urile pentru o carte
    public List<Review> getReviewsForBook(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}
