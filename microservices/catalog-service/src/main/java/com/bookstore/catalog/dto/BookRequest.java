package com.bookstore.catalog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record BookRequest(
        @NotBlank String title,
        @NotBlank String isbn,
        @Positive double price,
        @Min(0) int stock,
        String description,
        String imageUrl,
        List<Long> authorIds,
        List<Long> categoryIds
) {
}
