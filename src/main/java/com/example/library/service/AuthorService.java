package com.example.library.service;

import com.example.library.dto.AuthorDto;
import com.example.library.exception.UserAlreadyExistsException;
import com.example.library.maper.AuthorMapper;
import com.example.library.mod.Author;
import com.example.library.repo.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
/**
 * Сервис для управления сущностями Author.
 * Предоставляет методы для создания, обновления, удаления и получения авторов.
 */
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorMapper authorMapper;

    private final AuthorRepository authorRepository;
    /**
     * Создает нового автора.
     *
     * @param dto объект AuthorDto, содержащий информацию о новом авторе
     * @return AuthorDto созданного автора
     * @throws UserAlreadyExistsException если автор с таким именем уже существует
     */
    public AuthorDto create(AuthorDto dto) throws UserAlreadyExistsException {
        if (authorRepository.existsByName(dto.getName())) {
            throw new UserAlreadyExistsException("Автор с таким именем существует.");
        }
        Author author = authorMapper.toEntity(dto);
        Author resultAuthor = authorRepository.save(author);
        return authorMapper.toDto(resultAuthor);
    }
    /**
     * Обновляет существующего автора.
     *
     * @param id идентификатор автора
     * @param dto объект AuthorDto с обновленной информацией
     * @return AuthorDto обновленного автора
     * @throws ResponseStatusException если автор с указанным идентификатором не найден
     */
    public AuthorDto update(Long id, AuthorDto dto) {
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Автор с id `%s` не наден".formatted(id)));
        authorMapper.updateWithNull(dto, author);
        Author resultAuthor = authorRepository.save(author);
        return authorMapper.toDto(resultAuthor);
    }
    /**
     * Удаляет автора по идентификатору.
     *
     * @param id идентификатор автора
     * @return AuthorDto удаленного автора, или null, если автор не найден
     */
    public AuthorDto delete(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            authorRepository.delete(author);
        }
        return authorMapper.toDto(author);
    }
    /**
     * Получает автора по идентификатору.
     *
     * @param id идентификатор автора
     * @return AuthorDto найденного автора
     * @throws ResponseStatusException если автор с указанным идентификатором не найден
     */
    public AuthorDto getOne(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        return authorMapper.toDto(authorOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Автор с  id `%s` не наден".formatted(id))));
    }
}
