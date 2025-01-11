package com.example.library.service;

import com.example.library.dto.AuthorDto;
import com.example.library.exception.UserAlreadyExistsException;
import com.example.library.maper.AuthorMapper;
import com.example.library.mod.Author;
import com.example.library.repo.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тесты для AuthorService.
 * Проверяет логику создания, обновления, удаления и получения авторов.
 */
public class AuthorServiceTest {
    @InjectMocks
    private AuthorService authorService; // Сервис, который тестирую
    @Mock
    private AuthorMapper authorMapper; // Маппер для преобразования между Author и AuthorDto
    @Mock
    private AuthorRepository authorRepository; // Репозиторий для работы с данными авторов
    @Mock
    private AuthorDto authorDto; // DTO для автора
    @Mock
    private Author author; // Сущность автора

    /**
     * Подготовка данных перед каждым тестом.
     * Инициализирует объекты AuthorDto и Author, а также мока для зависимостей.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorDto = new AuthorDto("Test Author", "Test Country");
        author = new Author(1L, " Test Author", "Test Country");
    }

    /**
     * Проверяет, что метод create создает автора, если автор не существует.
     */
    @Test
    void create_ShouldCreateAuthor_WhenAuthorDoesNotExist() throws UserAlreadyExistsException {
        when(authorRepository.existsByName(authorDto.getName())).thenReturn(false);
        when(authorMapper.toEntity(authorDto)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(authorDto);
        AuthorDto createdAuthor = authorService.create(authorDto);
        assertNotNull(createdAuthor);
        assertEquals(authorDto.getName(), createdAuthor.getName());
        verify(authorRepository).save(author);
    }

    /**
     * Проверяет, что метод create выбрасывает исключение, если автор уже существует.
     */
    @Test
    void create_ShouldThrowException_WhenAuthorAlreadyExists() {
        when(authorRepository.existsByName(authorDto.getName())).thenReturn(true);
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            authorService.create(authorDto);
        });
        Assertions.assertEquals(null, exception.getMessage());
        verify(authorRepository, never()).save(any());
    }

    /**
     * Проверяет, что метод update выбрасывает исключение, если автор не существует.
     */
    @Test
    void update_ShouldThrowException_WhenAuthorDoesNotExist() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authorService.update(1L, authorDto);
        });
        assertEquals("404 NOT_FOUND \"Автор с id `1` не наден\"", exception.getMessage());
    }

    /**
     * Проверяет, что метод delete удаляет автора, если автор существует.
     */
    @Test
    void delete_ShouldDeleteAuthor_WhenAuthorExists() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(authorDto);
        AuthorDto deletedAuthor = authorService.delete(1L);
        assertNotNull(deletedAuthor);
        assertEquals(authorDto.getName(), deletedAuthor.getName());
        verify(authorRepository).delete(author);
    }

    /**
     * Проверяет, что метод delete возвращает null, если автор не существует.
     */
    @Test
    void delete_ShouldReturnNull_WhenAuthorDoesNotExist() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        AuthorDto deletedAuthor = authorService.delete(1L);
        assertNull(deletedAuthor);
        verify(authorRepository, never()).delete(any());
    }

    /**
     * Проверяет, что метод getOne возвращает автора, если автор существует.
     */
    @Test
    void getOne_ShouldReturnAuthor_WhenAuthorExists() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(authorDto);
        AuthorDto foundAuthor = authorService.getOne(1L);
        assertNotNull(foundAuthor);
        assertEquals(authorDto.getName(), foundAuthor.getName());
    }

    /**
     * Проверяет, что метод getOne выбрасывает исключение, если автор не существует.
     */
    @Test
    void getOne_ShouldThrowException_WhenAuthorDoesNotExist() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authorService.getOne(1L);
        });
        assertEquals("404 NOT_FOUND \"Автор с  id `1` не наден\"", exception.getMessage());
    }
}
