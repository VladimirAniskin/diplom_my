package com.example.library.repo;

import com.example.library.mod.BorrowRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс BorrowRecordsRepository, предоставляющий методы для работы с сущностью BorrowRecords.
 * Расширяет JpaRepository для использования стандартных операций CRUD.
 */
@Repository
public interface BorrowRecordsRepository extends JpaRepository<BorrowRecords, Long> {
    @Query("SELECT r FROM BorrowRecords r WHERE r.returnDate = :targetDate")
    List<BorrowRecords> findBorrowRecordsByReturnDate(@Param("targetDate") LocalDate targetDate);

    @Query("SELECT br.bookId.id, COUNT(br) FROM BorrowRecords br GROUP BY br.bookId.id ORDER BY COUNT(br) DESC")
    List<Object[]> findMostPopularBook();
}
