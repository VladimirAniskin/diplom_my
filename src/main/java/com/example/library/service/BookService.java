package com.example.library.service;
import com.example.library.filter.BookFilter;
import com.example.library.maper.BookMapper;
import com.example.library.dto.BookDto;
import com.example.library.mod.Book;
import com.example.library.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;


    public BookDto create(BookDto dto) {
        Book book = bookMapper.toEntity(dto);
        Book resultBook = bookRepository.save(book);
        return bookMapper.toDto(resultBook);
    }

    public BookDto update(Long id, BookDto dto) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        bookMapper.updateWithNull(dto, book);
        Book resultBook = bookRepository.save(book);
        return bookMapper.toDto(resultBook);
    }

    public BookDto delete(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            bookRepository.delete(book);
        }
        return bookMapper.toDto(book);
    }

    public BookDto getOne(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookMapper.toDto(bookOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public Page<BookDto> getList(BookFilter filter, Pageable pageable) {
        Specification<Book> spec = filter.toSpecification();
        Page<Book> books = bookRepository.findAll(spec, pageable);
        return books.map(bookMapper::toDto);
    }
}
