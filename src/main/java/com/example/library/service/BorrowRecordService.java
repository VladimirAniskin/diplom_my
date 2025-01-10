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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


/**
 * Сервис для управления записями о выдаче книг.
 * Предоставляет методы для аренды и возврата книг.
 */
@Service
@RequiredArgsConstructor
public class BorrowRecordService {

    private final BorrowRecordsMapper borrowRecordsMapper;

    private final BorrowRecordsRepository borrowRecordsRepository;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;


    /**
     * Оформляет аренду книги для пользователя.
     *
     * @param dto объект BorrowRecordsDto, содержащий информацию о пользователе и книге
     * @return BorrowRecordsDto созданной записи о выдаче книги
     * @throws EntityNotFoundException если пользователь или книга не найдены
     * @throws IllegalStateException   если нет доступных экземпляров книги
     */
    public BorrowRecordsDto bookRental(BorrowRecordsDto dto) {
        // Поиск пользователя по идентификатору
        User user = userRepository.findById(dto.getUserId().getId()).orElseThrow(() ->
                new EntityNotFoundException("Пользователь не наден с id: " + dto.getUserId().getId()));
        // Проверка существования книги и доступности экземпляров
        Book book = bookRepository.findById(dto.getBookId().getId()).orElseThrow(() ->
                new EntityNotFoundException("Книга не найдена с  id: " + dto.getBookId()));
        if (book.getNumberOfCopies() <= 0) {
            throw new IllegalStateException("Нет доступных экземпляров книг: " + book.getNumberOfCopies());
        }
        // Создание записи о выдаче книги
        BorrowRecords borrowRecords = borrowRecordsMapper.toEntity(dto);
        borrowRecords.setUserId(user);
        borrowRecords.setBookId(book);
        // Сохранение записи о выдаче
        BorrowRecords resultBorrowRecords = borrowRecordsRepository.save(borrowRecords);
        // Уменьшение количества экземпляров книги
        book.setNumberOfCopies(book.getNumberOfCopies() - 1);
        bookRepository.save(book);
        // Вызов метода для уведомления пользователя о необходимости вернуть книгу
        return borrowRecordsMapper.toDto(resultBorrowRecords);
    }

    /**
     * Обрабатывает возврат книги.
     *
     * @param id идентификатор записи о выдаче
     * @return BorrowRecordsDto обновленной записи о выдаче книги
     * @throws EntityNotFoundException если запись о выдаче не найдена
     */
    public BorrowRecordsDto bookReturn(Long id) {
        // Поиск записи о выдаче по идентификатору
        BorrowRecords borrowRecord = borrowRecordsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись о выдаче не надена с id: " + id));
        // Проверка даты возврата
        LocalDate returnDate = borrowRecord.getReturnDate();
        // Проверка задержки возврата если книга просрочена, сохранение даты возврата
        if (returnDate.isBefore(LocalDate.now())) {
            LocalDate dueDate = LocalDate.now();
            borrowRecord.setReturnDate(dueDate);
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            borrowRecord.setDueDate(dueDate.plusDays(daysLate));
            borrowRecordsRepository.save(borrowRecord);
        } else {
            returnDate = LocalDate.now();
            borrowRecord.setReturnDate(returnDate);
        }
        // Увеличение количества экземпляров книги
        Book book = borrowRecord.getBookId();
        book.setNumberOfCopies(book.getNumberOfCopies() + 1);
        bookRepository.save(book);
        // Сохранение обновленной записи о выдаче
        BorrowRecords updatedBorrowRecord = borrowRecordsRepository.save(borrowRecord);
        return borrowRecordsMapper.toDto(updatedBorrowRecord);
    }

    /**
     * Получает самую популярную книгу на основе количества займов.
     *
     * @return Book самая популярная книга
     */
    public Book getMostPopularBook() {
        List<Object[]> results = borrowRecordsRepository.findMostPopularBook();
        if (!results.isEmpty()) {
            Long bookId = (Long) results.get(0)[0]; // Получаем ID книги
            return bookRepository.findById(bookId).orElse(null);
        }
        return null; // Если нет записей о займах
    }


}
