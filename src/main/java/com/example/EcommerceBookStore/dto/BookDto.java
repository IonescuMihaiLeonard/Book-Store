package com.example.EcommerceBookStore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class BookDto {

    @NotBlank
    public String title;

    @NotBlank
    public String isbn;

    @Positive
    public double price;

    @Min(0)
    public int stock;

    public String description;

    public String imageUrl;

    public List<Long> authorIds;
    public List<Long> categoryIds;

    public String getTitle() {
        return title;
    }
}