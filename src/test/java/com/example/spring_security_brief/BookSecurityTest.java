package com.example.spring_security_brief;


import com.example.spring_security_brief.Entity.Book;
import com.example.spring_security_brief.Service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    @WithMockUser(authorities = "SCOPE_ROLE_USER")
    void userCanGetBooks() throws Exception {

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ROLE_USER")
    void userCannotDeleteBook() throws Exception {

        mockMvc.perform(delete("/api/books/" + UUID.randomUUID()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ROLE_ADMIN")

    void adminCanCreateBook() throws Exception {

        String body = """
                {
                  "title":"Clean Code",
                  "author":"Robert Martin",
                  "category":"Programming",
                  "yearOfPublication":2008,
                  "numberOfCopiesAvailable":5
                }
                """;

        mockMvc.perform(
                post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ROLE_ADMIN")
    void adminCanUpdateBook() throws Exception {

        Book book = new Book();

        when(bookService.updateBook(any(UUID.class), any(Book.class)))
                .thenReturn(book);

        String body = """
            {
              "title":"Updated",
              "author":"Martin",
              "category":"Programming",
              "yearOfPublication":2008,
              "numberOfCopiesAvailable":10
            }
            """;

        mockMvc.perform(
                put("/api/books/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ROLE_ADMIN")
    void adminCanDeleteBook() throws Exception {

        mockMvc.perform(
                delete("/api/books/" + UUID.randomUUID())
        ).andExpect(status().isOk());
    }
}