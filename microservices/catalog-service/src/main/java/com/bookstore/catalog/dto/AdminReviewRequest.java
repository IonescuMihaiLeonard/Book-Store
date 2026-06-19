package com.bookstore.catalog.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AdminReviewRequest(
        @NotNull Long bookId,
        @NotNull Long userId,
        @Min(1) @Max(5) int rating,
        String comment
) {
}
