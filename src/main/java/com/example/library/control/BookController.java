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

@RestController
@Tag(name = "Book", description = "Book")
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {

    private final   BookService bookService;


    @PostMapping(value = "/createBook")
    public BookDto create(@RequestBody @Valid BookDto dto) {
        return bookService.create(dto);
    }

    @PutMapping(value = "/upgrateBook/{id}")
    public BookDto update(@PathVariable Long id, @RequestBody @Valid BookDto dto) {
        return bookService.update(id, dto);
    }

    @DeleteMapping(value = "/deleteBook/{id}")
    public BookDto delete(@PathVariable Long id) {
        return bookService.delete(id);
    }

    @GetMapping(value = "/bookId/{id}")
    public BookDto getOne(@PathVariable Long id) {
        return bookService.getOne(id);
    }

    @GetMapping(value = "/getAllBook")
    public PagedModel<BookDto> getList(@ParameterObject @ModelAttribute BookFilter filter, @ParameterObject Pageable pageable) {
        Page<BookDto> bookDtos = bookService.getList(filter, pageable);
        return new PagedModel<>(bookDtos);
    }
}
