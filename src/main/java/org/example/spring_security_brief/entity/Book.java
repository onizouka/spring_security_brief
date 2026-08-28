package org.example.spring_security_brief.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID id;
    @Column(nullable = false)
    private String title;
    private String author;
    private String category;
    private Integer yearOfPublication;
    private Integer numberOfCopiesAvailable;

}
