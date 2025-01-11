package com.example.library.mod;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

/**
 * Класс сущности User, представляющий пользователя в системе.
 * Использует аннотации JPA для определения структуры таблицы в базе данных.
 * Использует Lombok для автоматической генерации методов доступа и других методов.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    /**
     * Уникальный идентификатор пользователя.
     * Генерируется автоматически при добавлении нового пользователя в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Имя пользователя.
     */
    private String name;
    /**
     * Электронная почта пользователя.
     * Проверяется на корректный формат с использованием аннотации @Email.
     */
    @Email(message = "В почте отсутствует @ или .ru ")
    private String email;
    /**
     * Дата регистрации пользователя.
     * Это поле не может быть обновлено после его установки.
     */
    @Column(name = "registration_date", updatable = false)
    private LocalDate registrationDate;

    /**
     * Метод, который вызывается перед сохранением пользователя в базу данных.
     * Устанавливает дату регистрации по умолчанию.
     */
    @PrePersist
    protected void onCreate() {

        this.registrationDate = LocalDate.now();
    }


}


