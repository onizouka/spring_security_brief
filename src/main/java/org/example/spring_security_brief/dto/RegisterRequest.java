package org.example.spring_security_brief.dto;

public record RegisterRequest(
        String username,
        String email,
        String password
) {
}