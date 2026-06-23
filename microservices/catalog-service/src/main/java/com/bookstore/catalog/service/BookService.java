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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

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

    public Page<Book> getAll(Pageable pageable) {
        log.info(
                "Loading books page number={} size={} sort={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort()
        );
        return bookRepository.findAll(pageable);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    @Transactional
    public Book create(BookRequest request) {
        log.info("Create book request received for title={} isbn={}", request.title(), request.isbn());
        if (bookRepository.existsByIsbn(request.isbn())) {
            log.warn("Create book rejected because ISBN already exists: {}", request.isbn());
            throw new IllegalArgumentException("ISBN already exists");
        }

        Book book = new Book();
        applyRequest(book, request);
        Book savedBook = bookRepository.save(book);
        log.info("Book created with id={} isbn={}", savedBook.getId(), savedBook.getIsbn());
        return savedBook;
    }

    @Transactional
    public Book update(Long id, BookRequest request) {
        log.info("Update book request received for id={}", id);
        Book book = getById(id);
        applyRequest(book, request);
        Book savedBook = bookRepository.save(book);
        log.info("Book updated with id={} isbn={}", savedBook.getId(), savedBook.getIsbn());
        return savedBook;
    }

    public void delete(Long id) {
        log.info("Delete book request received for id={}", id);
        bookRepository.deleteById(id);
        log.info("Book deleted with id={}", id);
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
