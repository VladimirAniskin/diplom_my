package com.example.library.service;
import com.example.library.dto.BookDto;
import com.example.library.maper.BookMapper;
import com.example.library.mod.Book;
import com.example.library.repo.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/**
 * Тесты для BookService.
 * Проверяет логику создания, обновления, удаления и получения книг.
 */
public class BookServiceTest {
    private BookService bookService; // Сервис, который тестирую
    private BookRepository bookRepository; // Мок репозитория книг
    private BookMapper bookMapper; // Мок маппера книг
    /**
     * Подготовка данных перед каждым тестом.
     * Инициализирует моки для BookRepository и BookMapper, а также BookService.
     */
    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookMapper = mock(BookMapper.class);
        bookService = new BookService(bookMapper, bookRepository);
    }
    /**
     * Проверяет, что метод create возвращает BookDto, когда книга успешно создана.
     */
    @Test
    void create_ShouldReturnBookDto_WhenBookIsCreated() {
        BookDto bookDto = new BookDto("Тест", new BookDto.AuthorDtoBook(1L), 2,3);
        Book book = new Book();
        Book savedBook = new Book();
        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(bookDto);
        BookDto result = bookService.create(bookDto);
        assertNotNull(result);
        verify(bookMapper).toEntity(bookDto);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(savedBook);
    }
    /**
     * Проверяет, что метод update возвращает обновленный BookDto, когда книга существует.
     */
    @Test
    void update_ShouldReturnUpdatedBookDto_WhenBookExists() {
        Long bookId = 1L;
        BookDto bookDto = new BookDto("Тест", new BookDto.AuthorDtoBook(1L), 2,3);
        Book existingBook = new Book();
        Book updatedBook = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookMapper.toDto(updatedBook)).thenReturn(bookDto);
        when(bookMapper.toDto(existingBook)).thenReturn(bookDto);
        when(bookRepository.save(existingBook)).thenReturn(updatedBook);
       BookDto result = bookService.update(bookId, bookDto);
        assertNotNull(result);
        verify(bookRepository).findById(bookId);
        verify(bookMapper).updateWithNull(bookDto, existingBook);
        verify(bookRepository).save(existingBook);
        verify(bookMapper).toDto(updatedBook);
    }
    /**
     * Проверяет, что метод update выбрасывает исключение, когда книга не существует.
     */
    @Test
    void update_ShouldThrowException_WhenBookDoesNotExist() {
        Long bookId = 1L;
        BookDto bookDto = new BookDto("Тест", new BookDto.AuthorDtoBook(1L), 2,3);
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            bookService.update(bookId, bookDto);
        });
        assertEquals("404 NOT_FOUND \"Книга с id `1` не найдена\"", exception.getMessage());
        verify(bookRepository).findById(bookId);
    }
    /**
     * Проверяет, что метод delete возвращает BookDto, когда книга существует.
     */
    @Test
    void delete_ShouldReturnBookDto_WhenBookExists() {
        Long bookId = 1L;
        Book book = new Book();
        BookDto bookDto = new BookDto("Тест", new BookDto.AuthorDtoBook(1L), 2,3);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        BookDto result = bookService.delete(bookId);
        assertNotNull(result);
        verify(bookRepository).findById(bookId);
        verify(bookRepository).delete(book);
        verify(bookMapper).toDto(book);
    }
    /**
     * Проверяет, что метод delete возвращает null, когда книга не существует.
     */
    @Test
    void delete_ShouldReturnNull_WhenBookDoesNotExist() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        BookDto result = bookService.delete(bookId);
        assertNull(result);
        verify(bookRepository).findById(bookId);
        verify(bookRepository, never()).delete((Book) any());
    }
    /**
     * Проверяет, что метод getOne возвращает BookDto, когда книга существует.
     */
    @Test
    void getOne_ShouldReturnBookDto_WhenBookExists() {
        Long bookId = 1L;
        Book book = new Book();
        BookDto bookDto = new BookDto("Тест", new BookDto.AuthorDtoBook(1L), 2,3);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        BookDto result = bookService.getOne(bookId);
        assertNotNull(result);
        verify(bookRepository).findById(bookId);
        verify(bookMapper).toDto(book);
    }
    /**
     * Проверяет, что метод getOne выбрасывает исключение, когда книга не существует.
     */
    @Test
    void getOne_ShouldThrowException_WhenBookDoesNotExist() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            bookService.getOne(bookId);
        });
        assertEquals("404 NOT_FOUND \"Книга с  id `1` не надена\"", exception.getMessage());
        verify(bookRepository).findById(bookId);
    }
}
