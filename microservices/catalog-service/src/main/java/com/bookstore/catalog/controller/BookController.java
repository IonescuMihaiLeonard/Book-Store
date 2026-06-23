package com.bookstore.catalog.controller;

import com.bookstore.catalog.dto.BookRequest;
import com.bookstore.catalog.model.Book;
import com.bookstore.catalog.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public Object getAllBooks(
            @PageableDefault(size = 10, sort = "title") Pageable pageable,
            @RequestParam MultiValueMap<String, String> params
    ) {
        if (hasPagingOrSorting(params)) {
            return bookService.getAll(pageable);
        }
        return bookService.getAll();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/admin/books")
    public Object getAdminBooks(
            @PageableDefault(size = 10, sort = "id") Pageable pageable,
            @RequestParam MultiValueMap<String, String> params
    ) {
        if (hasPagingOrSorting(params)) {
            return bookService.getAll(pageable);
        }
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

    private boolean hasPagingOrSorting(MultiValueMap<String, String> params) {
        return params.containsKey("page") || params.containsKey("size") || params.containsKey("sort");
    }
}
