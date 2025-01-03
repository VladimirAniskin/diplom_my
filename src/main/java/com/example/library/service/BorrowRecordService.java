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


@Service
@RequiredArgsConstructor
public class BorrowRecordService {

    private final BorrowRecordsMapper borrowRecordsMapper;

    private final BorrowRecordsRepository borrowRecordsRepository;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;


    public BorrowRecordsDto bookRental(BorrowRecordsDto dto) {
        User user = userRepository.findById(dto.getUserId().getId()).orElseThrow(() ->
                new EntityNotFoundException("User  not found with id: " + dto.getUserId().getId()));
        // Проверка существования книги и доступности экземпляров
        Book book = bookRepository.findById(dto.getBookId().getId()).orElseThrow(() ->
                new EntityNotFoundException("Book not found with id: " + dto.getBookId()));
        if (book.getNumberOfCopies() <= 0) {
            throw new IllegalStateException("No available copies of the book: " + book.getNumberOfCopies());
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
        return borrowRecordsMapper.toDto(resultBorrowRecords);
    }

    public BorrowRecordsDto bookReturn(Long id) {
        BorrowRecords borrowRecord = borrowRecordsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrow record not found with id: " + id));
        // Проверка даты возврата
        LocalDate returnDate = borrowRecord.getReturnDate();
        // Проверка задержки возврата если книга просрочена, сохранение даты возврата
        if (returnDate.isBefore(LocalDate.now())) {
            LocalDate dueDate = LocalDate.now();
            borrowRecord.setReturnDate(dueDate);
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            borrowRecord.setDueDate(dueDate.plusDays(daysLate));
            borrowRecordsRepository.save(borrowRecord);
        }else {
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
}
