package com.example.spring_security_brief;

import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginWithValidCredentialsReturnsJwt() throws Exception {

        String body = """
                {
                  "username":"admin",
                  "password":"admin123"
                }
                """;

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                                .content(body)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username")
                        .value("admin"));
    }

    @Test
    void loginWithInvalidCredentialsReturnsUnauthorized()
            throws Exception {

        String body = """
                {
                  "username":"admin",
                  "password":"wrongPassword"
                }
                """;

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                                .content(body)
                )
                .andExpect(status().isUnauthorized());
    }
}
