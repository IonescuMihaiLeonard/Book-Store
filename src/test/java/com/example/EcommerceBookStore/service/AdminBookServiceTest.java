package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.Books;
import com.example.EcommerceBookStore.model.repositoriy.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AdminBookService service;

    private Books book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Books();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setPrice(120);
        book.setStock(10);
    }

    @Test
    void testCreateBook() {
        when(bookRepository.save(any(Books.class))).thenReturn(book);

        Books result = service.create(book);
        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    void testUpdateBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Books.class))).thenReturn(book);

        book.setPrice(150);
        Books result = service.update(1L, book);
        assertEquals(150, result.getPrice());
    }

    @Test
    void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1L);
        service.delete(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        List<Books> list = service.getAll();
        assertEquals(1, list.size());
    }
}