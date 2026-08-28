package org.example.spring_security_brief.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email
) {
}
