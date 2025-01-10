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
@Builder
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;
    private Integer year;
    @Column (name="number_of_copies")
    private Integer numberOfCopies;



}
