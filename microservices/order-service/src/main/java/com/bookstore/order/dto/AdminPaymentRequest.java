package com.bookstore.order.dto;

import com.bookstore.order.model.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AdminPaymentRequest(
        @NotNull Long orderId,
        @NotNull PaymentStatus status,
        double amount,
        LocalDateTime paidAt
) {
}
