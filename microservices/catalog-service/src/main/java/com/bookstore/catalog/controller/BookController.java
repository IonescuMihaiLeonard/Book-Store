package com.bookstore.catalog.controller;

import com.bookstore.catalog.dto.BookRequest;
import com.bookstore.catalog.model.Book;
import com.bookstore.catalog.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/admin/books")
    public List<Book> getAdminBooks() {
        return bookService.getAll();
    }

    @PostMapping("/admin/books")
    public Book createBook(@Valid @RequestBody BookRequest request) {
        return bookService.create(request);
    }

    @PutMapping("/admin/books/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return bookService.update(id, request);
    }

    @DeleteMapping("/admin/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
}
