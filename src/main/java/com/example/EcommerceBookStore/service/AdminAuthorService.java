package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.Author;
import com.example.EcommerceBookStore.model.repositoriy.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminAuthorService {

    private final AuthorRepository authorRepository;

    public AdminAuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // CREATE
    public Author create(Author author) {
        return authorRepository.save(author);
    }

    // UPDATE
    public Author update(Long id, Author author) {

        Author existing = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        existing.setName(author.getName());

        return authorRepository.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    // GET ALL
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}