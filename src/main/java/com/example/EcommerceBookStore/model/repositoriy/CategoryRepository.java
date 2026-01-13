package com.example.EcommerceBookStore.model.repositoriy;

import com.example.EcommerceBookStore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
