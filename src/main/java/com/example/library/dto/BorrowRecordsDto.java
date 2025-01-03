package com.example.library.dto;

import lombok.Value;

/**
 * DTO (Data Transfer Object) для представления записей о заимствовании книг.
 * Используется для передачи информации о пользователе и книге, которые были заимствованы.
 *
 * @see com.example.library.mod.BorrowRecords
 */
@Value
public class BorrowRecordsDto {
    /**
     * Пользователь, который заимствовал книгу.
     * Содержит информацию об идентификаторе пользователя.
     */
    UserDtoBorrow userId;
    /**
     * Книга, которая была заимствована.
     * Содержит информацию об идентификаторе книги.
     */
    BookDtoBorrow bookId;


    /**
     * Вложенный DTO для представления пользователя, который заимствует книгу.
     *
     * @see com.example.library.mod.User
     */
    @Value
    public static class UserDtoBorrow {
        /**
         * Идентификатор пользователя.
         */
        Long id;
    }

    /**
     * Вложенный DTO для представления книги, которая была заимствована.
     *
     * @see com.example.library.mod.Book
     */
    @Value
    public static class BookDtoBorrow {
        /**
         * Идентификатор книги.
         */
        Long id;
    }
}