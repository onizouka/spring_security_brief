package org.example.spring_security_brief.dto;

public record BookRequest(
    String title,
    String author,
    String category,
    Integer yearOfPublication,
    Integer numberOfCopiesAvailable
    ){
}
