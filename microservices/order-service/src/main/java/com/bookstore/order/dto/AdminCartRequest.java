package com.bookstore.order.dto;

import jakarta.validation.constraints.NotNull;

public record AdminCartRequest(@NotNull Long userId) {
}
