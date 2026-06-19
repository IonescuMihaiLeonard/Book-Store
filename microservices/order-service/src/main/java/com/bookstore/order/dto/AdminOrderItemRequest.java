package com.bookstore.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminOrderItemRequest(
        @NotNull Long orderId,
        @NotNull Long bookId,
        @NotBlank String bookTitle,
        double price,
        @Min(1) int quantity
) {
}
