package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * DTO (Data Transfer Object) для представления данных автора.
 * Используется для передачи информации о авторе между слоями приложения.
 *
 * @see com.example.library.mod.Author
 */
@Value
public class AuthorDto {
    /**
     * Имя автора.
     * Не должно быть пустым или null.
     */
    String name;
    /**
     * Страна автора.
     * Не должно быть пустым или null.
     */
    @NotNull
    String country;
}