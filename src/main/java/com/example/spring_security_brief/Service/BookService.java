package com.example.spring_security_brief.Service;

import com.example.spring_security_brief.Entity.Book;
import com.example.spring_security_brief.Repository.BookRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository){
        this.repository = repository;
    }

    public List<Book> getAllBooks(){
        return repository.findAll();
    }

    public Book getBookById(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Livre introuvable"));
    }

    public Book createBook( Book book){
        return repository.save(book);
    }

    public Book updateBook(UUID id, Book book){
        Book existing = getBookById(id);

        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setCategory(book.getCategory());
        existing.setYearOfPublication(book.getYearOfPublication());
        existing.setNumberOfCopiesAvailable(book.getNumberOfCopiesAvailable());

        return repository.save(existing);

    }
    public void deleteBook(UUID id){
        repository.deleteById(id);
    }

}
