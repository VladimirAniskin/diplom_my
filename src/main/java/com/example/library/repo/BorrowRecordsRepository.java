package com.example.library.repo;

import com.example.library.mod.BorrowRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Интерфейс BorrowRecordsRepository, предоставляющий методы для работы с сущностью BorrowRecords.
 * Расширяет JpaRepository для использования стандартных операций CRUD.
 */
@Repository
public interface BorrowRecordsRepository extends JpaRepository<BorrowRecords, Long> {
}
