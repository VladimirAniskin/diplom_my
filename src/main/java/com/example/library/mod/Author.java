package com.example.library.mod;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

/**
 * Класс сущности Author, представляющий автора в системе.
 * Использует аннотации JPA для определения структуры таблицы в базе данных.
 * Использует Lombok для автоматической генерации методов доступа и других методов.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "author")
public class Author {
    /**
     * Уникальный идентификатор автора.
     * Генерируется автоматически при добавлении нового автора в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Имя автора.
     */
    private String name;
    /**
     * Страна автора.
     */
    private String country;


}
