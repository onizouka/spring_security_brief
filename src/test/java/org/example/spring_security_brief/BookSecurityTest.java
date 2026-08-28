package org.example.spring_security_brief;


import org.example.spring_security_brief.dto.BookDto;
import org.example.spring_security_brief.dto.BookUpdateDto;
import org.example.spring_security_brief.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    @WithMockUser(authorities = "USER_SCOPE")
    void userCanGetBooks() throws Exception {

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER_SCOPE")
    void userCannotDeleteBook() throws Exception {

        mockMvc.perform(delete("/api/books/" + UUID.randomUUID()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_SCOPE")

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
    @WithMockUser(authorities = "ADMIN_SCOPE")
    void adminCanUpdateBook(){

        BookDto result = new BookDto(
                UUID.randomUUID(),
                "Updated",
                "Martin",
                "Programming",
                2008,
                10
        );

        when(bookService.updateBook(
                any(UUID.class),
                any(BookUpdateDto.class)
        )).thenReturn(result);

    assertEquals("Updated", result.title());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_SCOPE")
    void adminCanDeleteBook() throws Exception {

        mockMvc.perform(
                delete("/api/books/" + UUID.randomUUID())
        ).andExpect(status().isOk());
    }
}