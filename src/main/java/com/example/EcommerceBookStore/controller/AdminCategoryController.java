package com.example.EcommerceBookStore.controller;

import com.example.EcommerceBookStore.model.Category;
import com.example.EcommerceBookStore.service.AdminCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    public AdminCategoryController(AdminCategoryService adminCategoryService) {
        this.adminCategoryService = adminCategoryService;
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return adminCategoryService.create(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id,
                           @RequestBody Category category) {
        return adminCategoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        adminCategoryService.delete(id);
    }

    @GetMapping
    public List<Category> getAll() {
        return adminCategoryService.getAll();
    }
}