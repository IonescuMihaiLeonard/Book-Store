package com.bookstore.order.dto;

import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(@NotNull Long addressId) {
}
