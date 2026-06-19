package com.bookstore.catalog.service;

import com.bookstore.catalog.dto.AdminReviewRequest;
import com.bookstore.catalog.dto.ReviewRequest;
import com.bookstore.catalog.model.Book;
import com.bookstore.catalog.model.Review;
import com.bookstore.catalog.repository.BookRepository;
import com.bookstore.catalog.repository.ReviewRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    public Review addReview(Long bookId, ReviewRequest request) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Review review = new Review();
        review.setBook(book);
        review.setUserId(request.userId());
        review.setRating(request.rating());
        review.setComment(request.comment());

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForBook(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
    }

    public Review createReview(AdminReviewRequest request) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Review review = new Review();
        review.setBook(book);
        review.setUserId(request.userId());
        review.setRating(request.rating());
        review.setComment(request.comment());
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, AdminReviewRequest request) {
        Review review = getReview(id);
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        review.setBook(book);
        review.setUserId(request.userId());
        review.setRating(request.rating());
        review.setComment(request.comment());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("Review not found");
        }
        reviewRepository.deleteById(id);
    }
}
