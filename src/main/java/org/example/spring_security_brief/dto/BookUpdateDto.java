package org.example.spring_security_brief.dto;

public record BookUpdateDto(
        String title,
        String author,
        String category,
        Integer yearOfPublication,
        Integer numberOfCopiesAvailable
) {
}
