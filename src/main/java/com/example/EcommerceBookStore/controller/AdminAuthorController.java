package com.example.EcommerceBookStore.controller;

import com.example.EcommerceBookStore.model.Author;
import com.example.EcommerceBookStore.service.AdminAuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/author")
public class AdminAuthorController {

    private final AdminAuthorService adminAuthorService;


    public AdminAuthorController(AdminAuthorService adminAuthorService) {
        this.adminAuthorService = adminAuthorService;
    }

    @PostMapping
    public Author create(@RequestBody Author author) {
        return adminAuthorService.create(author);
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id,
                         @RequestBody Author author) {
        return adminAuthorService.update(id, author);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        adminAuthorService.delete(id);
    }

    @GetMapping
    public List<Author> getAll() {
        return adminAuthorService.getAll();
    }
}
