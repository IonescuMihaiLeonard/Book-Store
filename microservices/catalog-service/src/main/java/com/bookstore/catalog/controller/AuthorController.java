package com.bookstore.catalog.controller;

import com.bookstore.catalog.model.Author;
import com.bookstore.catalog.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Object getAll(
            @PageableDefault(size = 10, sort = "name") Pageable pageable,
            @RequestParam MultiValueMap<String, String> params
    ) {
        if (hasPagingOrSorting(params)) {
            return authorService.getAll(pageable);
        }
        return authorService.getAll();
    }

    @PostMapping
    public Author create(@Valid @RequestBody Author author) {
        return authorService.create(author);
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, @Valid @RequestBody Author author) {
        return authorService.update(id, author);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }

    private boolean hasPagingOrSorting(MultiValueMap<String, String> params) {
        return params.containsKey("page") || params.containsKey("size") || params.containsKey("sort");
    }
}
