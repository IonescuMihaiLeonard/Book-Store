package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.Books;
import com.example.EcommerceBookStore.model.repositoriy.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Listare toate cărțile
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    // Căutare după titlu
    public List<Books> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    // Filtrare după autor
    public List<Books> searchByAuthor(String authorName) {
        return bookRepository.findByAuthors_Name(authorName);
    }

    // Filtrare după categorie
    public List<Books> searchByCategory(String categoryName) {
        return bookRepository.findByCategories_Name(categoryName);
    }

    // Obține detalii carte
    public Books getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}