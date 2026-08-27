package com.example.spring_security_brief.Controller;

import com.example.spring_security_brief.Service.BookService;
import org.springframework.web.bind.annotation.*;
import com.example.spring_security_brief.Entity.Book;

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
    public List<Book> getAllBooks(){
        return service.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable UUID id){
        return service.getBookById(id);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book){
        return service.createBook(book);
    }
    
    @PutMapping("/{id}")
    public Book updateBook(
            @PathVariable UUID id,
            @RequestBody Book book){
        return service.updateBook(id, book);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        service.deleteBook(id);
    }

    }
