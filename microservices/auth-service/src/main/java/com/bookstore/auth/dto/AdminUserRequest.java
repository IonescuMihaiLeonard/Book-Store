package com.bookstore.auth.dto;

import com.bookstore.auth.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminUserRequest(
        @NotBlank @Size(min = 3, max = 50) String username,
        @Email @NotBlank String email,
        String password,
        @NotNull Role role
) {
}
