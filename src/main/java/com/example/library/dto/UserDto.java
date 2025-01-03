package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * DTO for {@link com.example.library.mod.User}
 */
@Value
public class UserDto {
    @NotNull
    String name;
    @NotNull
    @Email(message = "В почте отсутствует @ или .ru ")
    String email;
}