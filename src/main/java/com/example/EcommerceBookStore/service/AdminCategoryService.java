package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.Category;
import com.example.EcommerceBookStore.model.repositoriy.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCategoryService {

    private final CategoryRepository categoryRepository;

    public AdminCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // CREATE
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    // UPDATE
    public Category update(Long id, Category category) {

        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setName(category.getName());

        return categoryRepository.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    // GET ALL
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}