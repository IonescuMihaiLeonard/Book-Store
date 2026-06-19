package com.bookstore.catalog.service;

import com.bookstore.catalog.dto.BookRequest;
import com.bookstore.catalog.model.Author;
import com.bookstore.catalog.model.Book;
import com.bookstore.catalog.model.Category;
import com.bookstore.catalog.repository.AuthorRepository;
import com.bookstore.catalog.repository.BookRepository;
import com.bookstore.catalog.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    @Transactional
    public Book create(BookRequest request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new IllegalArgumentException("ISBN already exists");
        }

        Book book = new Book();
        applyRequest(book, request);
        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, BookRequest request) {
        Book book = getById(id);
        applyRequest(book, request);
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private void applyRequest(Book book, BookRequest request) {
        book.setTitle(request.title());
        book.setIsbn(request.isbn());
        book.setPrice(request.price());
        book.setStock(request.stock());
        book.setDescription(request.description());
        book.setImageUrl(request.imageUrl());

        List<Author> authors = request.authorIds() == null ? List.of() : authorRepository.findAllById(request.authorIds());
        List<Category> categories = request.categoryIds() == null ? List.of() : categoryRepository.findAllById(request.categoryIds());

        book.setAuthors(authors);
        book.setCategories(categories);
    }
}
