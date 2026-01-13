package com.example.EcommerceBookStore.model.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EcommerceBookStore.model.Books;
import java.util.List;

public interface BookRepository extends JpaRepository<Books, Long> {

    List<Books> findByTitleContainingIgnoreCase(String title);

    List<Books> findByAuthors_Name(String name);

    List<Books> findByCategories_Name(String name);

    List<Books> findByPriceBetween(double min, double max);
}