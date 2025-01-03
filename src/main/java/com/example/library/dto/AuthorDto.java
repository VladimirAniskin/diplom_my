package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * DTO for {@link com.example.library.mod.Author}
 */
@Value
public class AuthorDto {
    @NotNull
    String name;
    @NotNull
    String country;
}