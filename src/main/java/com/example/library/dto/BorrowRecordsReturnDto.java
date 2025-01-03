package com.example.library.dto;

import lombok.Value;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для представления информации о возврате записей о заимствовании книг.
 * Используется для передачи данных о возврате книги, включая идентификатор записи заимствования и дату возврата.
 *
 * @see com.example.library.mod.BorrowRecords
 */
@Value
public class BorrowRecordsReturnDto {
    /**
     * Идентификатор записи о заимствовании.
     * Уникальный идентификатор, который связывает возврат с конкретной записью о заимствовании.
     */
    private Long borrowRecordId;
    /**
     * Дата возврата книги.
     * Указывает, когда книга была возвращена.
     */
    private LocalDate returnDate;

}