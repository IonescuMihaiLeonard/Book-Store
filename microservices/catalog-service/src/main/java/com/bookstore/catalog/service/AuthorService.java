package com.bookstore.catalog.service;

import com.bookstore.catalog.model.Author;
import com.bookstore.catalog.repository.AuthorRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Page<Author> getAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Author create(Author author) {
        return authorRepository.save(author);
    }

    public Author update(Long id, Author author) {
        Author existing = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        existing.setName(author.getName());
        return authorRepository.save(existing);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
