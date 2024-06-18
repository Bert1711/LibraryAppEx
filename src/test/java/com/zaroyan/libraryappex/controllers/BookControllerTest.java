package com.zaroyan.libraryappex.controllers;

import com.zaroyan.libraryappex.models.Author;
import com.zaroyan.libraryappex.models.Book;
import com.zaroyan.libraryappex.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Zaroyan
 */
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private List<Book> books;

    @BeforeEach
    public void setUp() {
        Author author1 = new Author(1L, "Author 1");
        Author author2 = new Author(2L, "Author 2");
        Author author3 = new Author(3L, "Author 3");

        books = Arrays.asList(
                new Book(1L, "Book 1", author1),
                new Book(2L, "Book 2", author1),
                new Book(3L, "Book 3", author1),
                new Book(4L, "Book 4", author2),
                new Book(5L, "Book 5", author2),
                new Book(6L, "Book 6", author2),
                new Book(7L, "Book 7", author2),
                new Book(8L, "Book 8", author3),
                new Book(9L, "Book 9", author3),
                new Book(10L, "Book 10", author3)
        );
    }

    @Test
    public void testGetAllBooksWithPagination() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookService.findAll(any(Pageable.class))).thenReturn(bookPage);

        mockMvc.perform(get("/api/books")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(10))
                .andExpect(jsonPath("$.content[0].title").value("Book 1"))
                .andExpect(jsonPath("$.content[0].author.name").value("Author 1"))
                .andExpect(jsonPath("$.content[9].title").value("Book 10"))
                .andExpect(jsonPath("$.content[9].author.name").value("Author 3"))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.totalElements").value(10));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = books.get(0);

        when(bookService.findUserById(book.getId())).thenReturn(book);

        mockMvc.perform(get("/api/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author.name").value(book.getAuthor().getName()));
    }
}
