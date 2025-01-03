package com.example.library.control;
import com.example.library.dto.AuthorDto;
import com.example.library.exception.UserAlreadyExistsException;
import com.example.library.service.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * Контроллер для работы с авторами.
 * Предоставляет REST API для создания, обновления, удаления и получения информации об авторах.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Author", description = "Author API")
@RequestMapping("/api")
public class AuthorController {


    private final AuthorService authorService;
    /**
     * Создает нового автора.
     *
     * @param dto объект передачи данных автора, который содержит информацию о новом авторе
     * @return созданный объект AuthorDto
     * @throws UserAlreadyExistsException если автор с таким именем уже существует
     */
    @PostMapping(value = "/createAuthor")
    public AuthorDto create(@RequestBody @Valid AuthorDto dto) throws UserAlreadyExistsException {
        return authorService.create(dto);
    }
    /**
     * Обновляет информацию об авторе по его идентификатору.
     *
     * @param id  идентификатор автора, информацию которого нужно обновить
     * @param dto объект передачи данных автора с обновленной информацией
     * @return обновленный объект AuthorDto
     */
    @PutMapping(value = "/upgrateAuthor/{id}")
    public AuthorDto update(@PathVariable Long id, @RequestBody @Valid AuthorDto dto) {
        return authorService.update(id, dto);
    }
    /**
     * Удаляет автора по его идентификатору.
     *
     * @param id идентификатор автора, которого нужно удалить
     * @return объект AuthorDto, представляющий удаленного автора
     */
    @DeleteMapping(value = "/deleteAuthor/{id}")
    public AuthorDto delete(@PathVariable Long id) {
        return authorService.delete(id);
    }
    /**
     * Получает информацию об авторе по его идентификатору.
     *
     * @param id идентификатор автора, информацию о котором нужно получить
     * @return объект AuthorDto, представляющий запрашиваемого автора
     */
    @GetMapping(value = "/AuthorId/{id}")
    public AuthorDto getOne(@PathVariable Long id) {
        return authorService.getOne(id);
    }
}
