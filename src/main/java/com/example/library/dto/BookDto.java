package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * DTO (Data Transfer Object) для представления данных книги.
 * Используется для передачи информации о книге между слоями приложения.
 *
 * @see com.example.library.mod.Book
 */
@Value
public class BookDto {
    /**
     * Название книги.
     * Не должно быть пустым или null.
     */
    @NotNull(message = "Имя не может быть пустым")
    String name;
    /**
     * Автор книги.
     * Не должен быть null.
     */
    @NotNull
    BookDto.AuthorDtoBook author;
    /**
     * Год издания книги.
     * Не должен быть пустым или null.
     */
    @NotNull(message = "Год не может быть пустым")
    Integer year;
    /**
     * Количество копий книги.
     * Может быть null, если количество не указано.
     */
    Integer numberOfCopies;

    /**
     * Вложенный DTO для представления автора книги.
     * Используется для передачи информации о авторе.
     *
     * @see com.example.library.mod.Author
     */
    @Value
    public static class AuthorDtoBook {
        /**
         * Идентификатор автора.
         */
        Long id;
    }
}