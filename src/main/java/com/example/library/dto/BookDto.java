package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * DTO for {@link com.example.library.mod.Book}
 */
@Value
public class BookDto {
    @NotNull(message = "Имя не может быть пустым")
    String name;
    @NotNull
    BookDto.AuthorDtoBook author;
    @NotNull(message = "Год не может быть пустым")
    Integer year;
    Integer numberOfCopies;

    /**
     * DTO for {@link com.example.library.mod.Author}
     */
    @Value
    public static class AuthorDtoBook {
        Long id;
    }
}