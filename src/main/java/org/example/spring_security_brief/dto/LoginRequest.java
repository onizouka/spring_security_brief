package org.example.spring_security_brief.dto;


public record LoginRequest(
        String username,
        String password
) {
}
