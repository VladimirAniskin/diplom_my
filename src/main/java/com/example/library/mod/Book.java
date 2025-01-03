package com.example.library.mod;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;

/**
 * Класс сущности Book, представляющий книгу в системе.
 * Использует аннотации JPA для определения структуры таблицы в базе данных.
 * Использует Lombok для автоматической генерации методов доступа и других методов.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Lazy
@Table(name = "books")
public class Book {
    /**
     * Уникальный идентификатор книги.
     * Генерируется автоматически при добавлении новой книги в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Название книги.
     */
    private String name;
    /**
     * Автор книги.
     * Связь многие-к-одному с сущностью Author.
     */
    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;
    /**
     * Год публикации книги.
     */
    private Integer year;
    /**
     * Количество копий книги в наличии.
     */
    @Column (name="number_of_copiis")
    private Integer numberOfCopies;

}
