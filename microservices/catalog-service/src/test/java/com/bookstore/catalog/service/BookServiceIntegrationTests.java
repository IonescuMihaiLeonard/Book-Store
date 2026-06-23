package com.bookstore.catalog.service;

import com.bookstore.catalog.dto.BookRequest;
import com.bookstore.catalog.model.Author;
import com.bookstore.catalog.model.Book;
import com.bookstore.catalog.model.Category;
import com.bookstore.catalog.repository.AuthorRepository;
import com.bookstore.catalog.repository.BookRepository;
import com.bookstore.catalog.repository.CategoryRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class BookServiceIntegrationTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void cleanDatabase() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void createBookWithAuthorAndCategory() {
        Author author = new Author();
        author.setName("George Orwell");
        author = authorRepository.save(author);

        Category category = new Category();
        category.setName("Dystopian");
        category = categoryRepository.save(category);

        Book book = bookService.create(new BookRequest(
                "1984",
                "ISBN-1984-TEST",
                45.50,
                12,
                "Roman clasic",
                null,
                List.of(author.getId()),
                List.of(category.getId())
        ));

        assertThat(book.getId()).isNotNull();
        assertThat(book.getAuthors()).extracting(Author::getName).containsExactly("George Orwell");
        assertThat(book.getCategories()).extracting(Category::getName).containsExactly("Dystopian");
    }

    @Test
    void createBookRejectsDuplicateIsbn() {
        BookRequest request = new BookRequest(
                "Clean Code",
                "ISBN-DUPLICATE",
                80.00,
                5,
                "Carte tehnica",
                null,
                List.of(),
                List.of()
        );

        bookService.create(request);

        assertThatThrownBy(() -> bookService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ISBN already exists");
    }

    @Test
    void paginatedBooksReturnExpectedPage() {
        bookService.create(new BookRequest("A Book", "ISBN-A", 10.00, 3, null, null, List.of(), List.of()));
        bookService.create(new BookRequest("B Book", "ISBN-B", 20.00, 4, null, null, List.of(), List.of()));

        var page = bookService.getAll(PageRequest.of(0, 1));

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getTotalPages()).isEqualTo(2);
    }
}
