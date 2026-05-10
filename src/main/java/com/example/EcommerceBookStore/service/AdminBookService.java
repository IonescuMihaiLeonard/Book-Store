package com.example.EcommerceBookStore.service;


import com.example.EcommerceBookStore.dto.BookDto;
import com.example.EcommerceBookStore.model.Author;
import com.example.EcommerceBookStore.model.Books;
import com.example.EcommerceBookStore.model.Category;
import com.example.EcommerceBookStore.model.repositoriy.AuthorRepository;
import com.example.EcommerceBookStore.model.repositoriy.BookRepository;
import com.example.EcommerceBookStore.model.repositoriy.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public AdminBookService(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Books create(BookDto req) {

        Books book = new Books();
        book.setTitle(req.title);
        book.setIsbn(req.isbn);
        book.setPrice(req.price);
        book.setStock(req.stock);
        book.setDescription(req.description);
        book.setImageUrl(req.imageUrl);

        // 🔑 autori
        if (req.authorIds != null && !req.authorIds.isEmpty()) {
            List<Author> authors = authorRepository.findAllById(req.authorIds);
            book.setAuthors(authors);
        }

        // 🔑 categorii
        if (req.categoryIds != null && !req.categoryIds.isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(req.categoryIds);
            book.setCategories(categories);
        }

        return bookRepository.save(book);
    }

    public Books update(Long id, Books book) {
        Books existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        existing.setTitle(book.getTitle());
        existing.setPrice(book.getPrice());
        existing.setStock(book.getStock());
        existing.setIsbn(book.getIsbn());
        existing.setDescription(book.getDescription());
        existing.setImageUrl(book.getImageUrl());

        return bookRepository.save(existing);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Books> getAll() {
        return bookRepository.findAll();
    }

}
