package com.bookstore.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminCartItemRequest(
        @NotNull Long cartId,
        @NotNull Long bookId,
        @NotBlank String bookTitle,
        double unitPrice,
        @Min(1) int quantity
) {
}
