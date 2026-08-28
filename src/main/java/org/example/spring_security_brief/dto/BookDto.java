package org.example.spring_security_brief.dto;

import java.util.UUID;

public record BookDto(
        UUID id,
        String title,
        String author,
        String category,
        Integer yearOfPublication,
        Integer numberOfCopiesAvailable
) {}