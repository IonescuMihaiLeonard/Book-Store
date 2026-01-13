package com.example.EcommerceBookStore.service;


import com.example.EcommerceBookStore.model.Books;
import com.example.EcommerceBookStore.model.repositoriy.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBookService {

    private final BookRepository bookRepository;

    public AdminBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Books create(Books book) {
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

        return bookRepository.save(existing);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Books> getAll() {
        return bookRepository.findAll();
    }
}
