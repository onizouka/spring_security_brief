package com.example.spring_security_brief;

import com.example.spring_security_brief.Entity.UserEntity;
import com.example.spring_security_brief.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }
    @Test
    void registerReturnsCreated() throws Exception {

        String body = """
                {
                  "username":"julien",
                  "email":"julien@test.com",
                  "password":"azerty123"
                }
                """;

        mockMvc.perform(
                post("/auth/register")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(body)
        ).andExpect(status().isCreated());
    }

    @Test
    void registerCreatesUser() throws Exception {

        String body = """
                {
                  "username":"user-test",
                  "email":"user@test.com",
                  "password":"password123"
                }
                """;

        mockMvc.perform(
                post("/auth/register")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(body)
        );

        assertTrue(
                userRepository.findByUsername("user-test")
                        .isPresent()
        );
    }

    @Test
    void passwordIsNotStoredInPlainText() throws Exception {

        String body = """
                {
                  "username":"user-secure",
                  "email":"secure@test.com",
                  "password":"password123"
                }
                """;

        mockMvc.perform(
                post("/auth/register")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(body)
        );

        UserEntity user = userRepository
                .findByUsername("user-secure")
                .orElseThrow();

        assertNotEquals(
                "password123",
                user.getPassword()
        );
    }
}
