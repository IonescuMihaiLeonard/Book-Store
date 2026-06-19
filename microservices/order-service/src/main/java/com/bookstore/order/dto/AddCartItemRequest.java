package com.bookstore.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddCartItemRequest(
        @NotNull Long bookId,
        @NotBlank String bookTitle,
        @Positive double unitPrice,
        @Min(1) int quantity
) {
}
