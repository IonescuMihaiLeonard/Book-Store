package com.bookstore.catalog.controller;

import com.bookstore.catalog.dto.ReviewRequest;
import com.bookstore.catalog.model.Review;
import com.bookstore.catalog.service.ReviewService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books/{bookId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getReviews(@PathVariable Long bookId) {
        return reviewService.getReviewsForBook(bookId);
    }

    @PostMapping
    public Review addReview(@PathVariable Long bookId, @Valid @RequestBody ReviewRequest request) {
        return reviewService.addReview(bookId, request);
    }
}
