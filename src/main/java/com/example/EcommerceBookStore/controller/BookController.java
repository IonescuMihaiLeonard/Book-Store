package com.example.EcommerceBookStore.controller;

import com.example.EcommerceBookStore.model.Books;
import com.example.EcommerceBookStore.model.repositoriy.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    // 1️⃣ List all books
    @GetMapping
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    // 2️⃣ Get book by ID
    @GetMapping("/{id}")
    public Books getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
