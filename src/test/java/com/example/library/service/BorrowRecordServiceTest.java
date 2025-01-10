package com.example.library.service;
import com.example.library.dto.BorrowRecordsDto;
import com.example.library.maper.BorrowRecordsMapper;
import com.example.library.mod.BorrowRecords;
import com.example.library.mod.User;
import com.example.library.mod.Book;
import com.example.library.repo.BookRepository;
import com.example.library.repo.BorrowRecordsRepository;
import com.example.library.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Тесты для BorrowRecordService.
 * Проверяет логику аренды и возврата книг, а также обработку ошибок.
 */
public class BorrowRecordServiceTest {
    @InjectMocks
    private BorrowRecordService borrowRecordService; // Сервис, который тестирую
    @Mock
    private BorrowRecordsMapper borrowRecordsMapper; // Мок маппера записей о выдаче
    @Mock
    private BorrowRecordsRepository borrowRecordsRepository; // Мок репозитория записей о выдаче
    @Mock
    private UserRepository userRepository; // Мок репозитория пользователей
    @Mock
    private BookRepository bookRepository; // Мок репозитория книг

    /**
     * Подготовка данных перед каждым тестом.
     * Инициализирует моки для всех зависимостей сервиса BorrowRecordService.
     */
    @BeforeEach
    void setUp() {
        borrowRecordsMapper = mock(BorrowRecordsMapper.class);
        borrowRecordsRepository = mock(BorrowRecordsRepository.class);
        userRepository = mock(UserRepository.class);
        bookRepository = mock(BookRepository.class);
        borrowRecordService = new BorrowRecordService(borrowRecordsMapper,
                borrowRecordsRepository, userRepository, bookRepository);
    }
    /**
     * Проверяет, что метод bookRental возвращает BorrowRecordsDto, когда книга успешно арендована.
     */
    @Test
    void bookRental_ShouldReturnBorrowRecordsDto_WhenBookIsRented() {
        BorrowRecordsDto borrowRecordsDto = new BorrowRecordsDto(new BorrowRecordsDto.UserDtoBorrow(1L),
                new BorrowRecordsDto.BookDtoBorrow(1L));
        User user = new User();
        user.setId(1L);
        Book book = new Book();
        book.setId(1L);
        book.setNumberOfCopies(1); // Доступно 1 экземпляр
        BorrowRecords borrowRecords = new BorrowRecords();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(borrowRecordsMapper.toEntity(borrowRecordsDto)).thenReturn(borrowRecords);
        when(borrowRecordsRepository.save(borrowRecords)).thenReturn(borrowRecords);
        when(borrowRecordsMapper.toDto(borrowRecords)).thenReturn(borrowRecordsDto);
        BorrowRecordsDto result = borrowRecordService.bookRental(borrowRecordsDto);
        assertNotNull(result);
        assertEquals(user.getId(), result.getUserId().getId());
        assertEquals(book.getId(), result.getBookId().getId());
        verify(bookRepository).save(book); // Проверка, что книга была сохранена
    }
    /**
     * Проверяет, что метод bookRental выбрасывает EntityNotFoundException, когда книга не найдена.
     */
    @Test
    void bookRental_ShouldThrowEntityNotFoundException_WhenBookNotFound() {
        BorrowRecordsDto borrowRecordsDto = new BorrowRecordsDto(new BorrowRecordsDto.UserDtoBorrow(1L),
                new BorrowRecordsDto.BookDtoBorrow(1L));
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(borrowRecordsDto.getBookId().getId())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            borrowRecordService.bookRental(borrowRecordsDto);
        });
        assertEquals("Книга не найдена с  id: " + borrowRecordsDto.getBookId(), exception.getMessage());
    }
    /**
     * Проверяет, что метод bookRental выбрасывает IllegalStateException, когда нет доступных экземпляров книги.
     */
    @Test
    void bookRental_ShouldThrowIllegalStateException_WhenNoCopiesAvailable() {

        BorrowRecordsDto borrowRecordsDto = new BorrowRecordsDto(new BorrowRecordsDto.UserDtoBorrow(1L),
                new BorrowRecordsDto.BookDtoBorrow(1L));
        User user = new User();
        user.setId(1L);
        Book book = new Book();
        book.setId(1L);
        book.setNumberOfCopies(0); // Нет доступных экземпляров
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            borrowRecordService.bookRental(borrowRecordsDto);
        });
        assertEquals("Нет доступных экземпляров книг: 0", exception.getMessage());
    }
    /**
     * Проверяет, что метод bookReturn выбрасывает EntityNotFoundException, когда запись о выдаче не найдена.
     */
    @Test
    void bookReturn_ShouldThrowEntityNotFoundException_WhenBorrowRecordNotFound() {
        Long borrowRecordId = 1L;
        when(borrowRecordsRepository.findById(borrowRecordId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            borrowRecordService.bookReturn(borrowRecordId);
        });
        assertEquals("Запись о выдаче не надена с id: " + borrowRecordId, exception.getMessage());
    }
}
