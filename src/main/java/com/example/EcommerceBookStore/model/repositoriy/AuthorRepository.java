package com.example.EcommerceBookStore.model.repositoriy;

import com.example.EcommerceBookStore.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);
}