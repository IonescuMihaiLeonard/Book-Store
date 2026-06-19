package com.bookstore.auth.dto;

public record AuthResponse(String token, String username, String email, String role) {
}
