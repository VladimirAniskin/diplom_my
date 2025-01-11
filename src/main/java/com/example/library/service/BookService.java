package com.example.library.service;

import com.example.library.filter.BookFilter;
import com.example.library.maper.BookMapper;
import com.example.library.dto.BookDto;
import com.example.library.mod.Book;
import com.example.library.repo.BookRepository;
import com.example.library.repo.BorrowRecordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Сервис для управления сущностями Book.
 * Предоставляет методы для создания, обновления, удаления и получения книг.
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;


    /**
     * Создает новую книгу.
     *
     * @param dto объект BookDto, содержащий информацию о новой книге
     * @return BookDto созданной книги
     */
    public BookDto create(BookDto dto) {
        Book book = bookMapper.toEntity(dto);
        Book resultBook = bookRepository.save(book);
        return bookMapper.toDto(resultBook);
    }

    /**
     * Обновляет существующую книгу.
     *
     * @param id  идентификатор книги
     * @param dto объект BookDto с обновленной информацией
     * @return BookDto обновленной книги
     * @throws ResponseStatusException если книга с указанным идентификатором не найдена
     */
    public BookDto update(Long id, BookDto dto) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга с id `%s` не найдена".formatted(id)));
        bookMapper.updateWithNull(dto, book);
        Book resultBook = bookRepository.save(book);
        return bookMapper.toDto(resultBook);
    }

    /**
     * Удаляет книгу по идентификатору.
     *
     * @param id идентификатор книги
     * @return BookDto удаленной книги, или null, если книга не найдена
     */
    public BookDto delete(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            bookRepository.delete(book);
        }
        return bookMapper.toDto(book);
    }

    /**
     * Получает книгу по идентификатору.
     *
     * @param id идентификатор книги
     * @return BookDto найденной книги
     * @throws ResponseStatusException если книга с указанным идентификатором не найдена
     */
    public BookDto getOne(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookMapper.toDto(bookOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга с  id `%s` не надена".formatted(id))));
    }

    /**
     * Получает список книг с применением фильтрации и пагинации.
     *
     * @param filter   объект BookFilter для задания условий фильтрации
     * @param pageable объект Pageable для задания параметров пагинации
     * @return Page<BookDto> страница с книгами, соответствующими условиям фильтрации
     */
    public Page<BookDto> getList(BookFilter filter, Pageable pageable) {
        Specification<Book> spec = filter.toSpecification();
        Page<Book> books = bookRepository.findAll(spec, pageable);
        return books.map(bookMapper::toDto);
    }


}
