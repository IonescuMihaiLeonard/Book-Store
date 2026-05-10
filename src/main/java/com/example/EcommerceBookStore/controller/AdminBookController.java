package com.example.EcommerceBookStore.controller;


import com.example.EcommerceBookStore.dto.BookDto;
import com.example.EcommerceBookStore.model.Books;
import com.example.EcommerceBookStore.service.AdminBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/books")
public class AdminBookController {

    private final AdminBookService service;

    public AdminBookController(AdminBookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Books> createBook(
            @RequestBody BookDto request
    ) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public Books update(@PathVariable Long id, @RequestBody Books book) {
        return service.update(id, book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public List<Books> getAll() {
        return service.getAll();
    }
}