package com.example.EcommerceBookStore.controller;


import com.example.EcommerceBookStore.model.Books;
import com.example.EcommerceBookStore.service.AdminBookService;
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
    public Books create(@RequestBody Books book) {
        return service.create(book);
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