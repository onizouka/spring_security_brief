package org.example.spring_security_brief.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.spring_security_brief.entity.Book;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
