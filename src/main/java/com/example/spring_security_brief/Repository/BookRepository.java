package com.example.spring_security_brief.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.spring_security_brief.Entity.Book;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
