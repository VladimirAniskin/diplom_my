package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

/**
 * DTO (Data Transfer Object) для представления пользователя.
 * Используется для передачи информации о пользователе, включая его имя и адрес электронной почты.
 *
 * @see com.example.library.mod.User
 */
@Value
@Setter
public class UserDto {
    /**
     * Имя пользователя.
     * Поле не может быть null.
     */
    @NotNull
    String name;
    /**
     * Электронная почта пользователя.
     * Поле не может быть null и должно содержать корректный адрес электронной почты.
     * Сообщение об ошибке будет выдано, если адрес электронной почты не соответствует формату.
     */
    @NotNull
    @Email(message = "В почте отсутствует @ или .ru ")
    String email;
    /**
     * Для теста
     */

    public void setId(long l) {
    }
}