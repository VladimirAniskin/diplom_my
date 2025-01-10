package com.example.library.control;
import com.example.library.dto.BookDto;
import com.example.library.filter.BookFilter;
import com.example.library.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Контроллер для работы с книгами.
 * Предоставляет REST API для создания, обновления, удаления и получения информации о книгах.
 */

@RestController
@Tag(name = "Book", description = "Book")
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {

    private final   BookService bookService;
    /**
     * Создает новую книгу.
     *
     * @param dto объект передачи данных книги, который содержит информацию о новой книге
     * @return созданный объект BookDto
     */

    /**
     * Обновляет информацию о книге по ее идентификатору.
     *
     * @param id  идентификатор книги, информацию которой нужно обновить
     * @param dto объект передачи данных книги с обновленной информацией
     * @return обновленный объект BookDto
     */

    /**
     * Удаляет книгу по ее идентификатору.
     *
     * @param id идентификатор книги, которую нужно удалить
     * @return объект BookDto, представляющий удаленную книгу
     */

    @DeleteMapping(value = "/deleteBook/{id}")
    public BookDto delete(@PathVariable Long id) {
        return bookService.delete(id);
    }
    /**
     * Получает информацию о книге по ее идентификатору.
     *
     * @param id идентификатор книги, информацию о которой нужно получить
     * @return объект BookDto, представляющий запрашиваемую книгу
     */
    @GetMapping(value = "/bookId/{id}")
    public BookDto getOne(@PathVariable Long id) {
        return bookService.getOne(id);
    }

    /**
     * Получает список книг с возможностью фильтрации и постраничного отображения.
     *
     * @param filter  объект фильтрации для поиска книг
     * @param pageable объект, содержащий информацию о постраничной навигации
     * @return PagedModel<BookDto>, содержащий список книг и информацию о пагинации
     */
    @GetMapping(value = "/getAllBook")
    public PagedModel<BookDto> getList(@ParameterObject @ModelAttribute BookFilter filter, @ParameterObject Pageable pageable) {
        Page<BookDto> bookDtos = bookService.getList(filter, pageable);
        return new PagedModel<>(bookDtos);
    }

    @PutMapping("/bookUpdate/{id}")
    public BookDto update ( @PathVariable Long id, @RequestBody @Valid BookDto dto ) {
        return bookService.update(id, dto);
    }

    @PostMapping("/creareBook")
    public BookDto create ( @RequestBody @Valid BookDto dto ) {
        return bookService.create(dto);
    }
}
