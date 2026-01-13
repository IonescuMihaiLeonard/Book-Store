package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.Author;
import com.example.EcommerceBookStore.model.Books;
import com.example.EcommerceBookStore.model.Category;
import com.example.EcommerceBookStore.model.repositoriy.AuthorRepository;
import com.example.EcommerceBookStore.model.repositoriy.BookRepository;
import com.example.EcommerceBookStore.model.repositoriy.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCatalogService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public AdminCatalogService(BookRepository bookRepository,
                               AuthorRepository authorRepository,
                               CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    // ----------------- Book CRUD -----------------
    public Books createBook(Books book) {
        return bookRepository.save(book);
    }

    public Books updateBook(Long id, Books updatedBook) {
        Books book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(updatedBook.getTitle());
        book.setIsbn(updatedBook.getIsbn());
        book.setPrice(updatedBook.getPrice());
        book.setStock(updatedBook.getStock());
        book.setDescription(updatedBook.getDescription());
        book.setAuthors(updatedBook.getAuthors());
        book.setCategories(updatedBook.getCategories());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    // ----------------- Author CRUD -----------------
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.setName(updatedAuthor.getName());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    // ----------------- Category CRUD -----------------
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(updatedCategory.getName());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
