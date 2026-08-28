package org.example.spring_security_brief.controller;

import org.example.spring_security_brief.dto.BookDto;
import org.example.spring_security_brief.dto.BookRequest;
import org.example.spring_security_brief.dto.BookUpdateDto;
import org.example.spring_security_brief.service.BookService;
import org.springframework.web.bind.annotation.*;
import org.example.spring_security_brief.entity.Book;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service){
        this.service = service;
    }
    @GetMapping
    public List<BookDto> getAllBooks(){
        return service.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable UUID id){
        return service.getBookById(id);
    }

    @PostMapping
    public Book createBook(@RequestBody BookRequest request){
        return service.createBook(request);
    }
    
    @PutMapping("/{id}")
    public BookDto updateBook(
            @PathVariable UUID id,
            @RequestBody BookUpdateDto dto){
        return service.updateBook(id, dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        service.deleteBook(id);
    }

    }
