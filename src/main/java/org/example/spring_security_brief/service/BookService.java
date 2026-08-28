package org.example.spring_security_brief.service;

import org.example.spring_security_brief.dto.BookDto;
import org.example.spring_security_brief.dto.BookRequest;
import org.example.spring_security_brief.dto.BookUpdateDto;
import org.example.spring_security_brief.entity.Book;
import org.example.spring_security_brief.repository.BookRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository){
        this.repository = repository;
    }

    public List<BookDto> getAllBooks(){
        return repository.findAll()
                .stream()
                .map(book -> new BookDto(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getCategory(),
                        book.getYearOfPublication(),
                        book.getNumberOfCopiesAvailable()
                ))
        .toList();
    }

    public BookDto getBookById(UUID id){
        Book book = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Livre introuvable"));
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getYearOfPublication(),
                book.getNumberOfCopiesAvailable()
        );
    }

    public Book createBook( BookRequest request){
        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setYearOfPublication(request.yearOfPublication());
        book.setNumberOfCopiesAvailable(request.numberOfCopiesAvailable());
        return repository.save(book);
    }

    public BookDto updateBook(UUID id, BookUpdateDto dto) {

        Book book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setCategory(dto.category());
        book.setYearOfPublication(dto.yearOfPublication());
        book.setNumberOfCopiesAvailable(dto.numberOfCopiesAvailable());

        Book saved = repository.save(book);

        return new BookDto(
                saved.getId(),
                saved.getTitle(),
                saved.getAuthor(),
                saved.getCategory(),
                saved.getYearOfPublication(),
                saved.getNumberOfCopiesAvailable()
        );
    }
    public void deleteBook(UUID id){
        repository.deleteById(id);
    }

}
