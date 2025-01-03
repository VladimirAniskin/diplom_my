package com.example.library.mod;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
/**
 * Класс сущности BorrowRecords, представляющий записи о заемах книг в системе.
 * Использует аннотации JPA для определения структуры таблицы в базе данных.
 * Использует Lombok для автоматической генерации методов доступа и других методов.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BorrowRecords {
    /**
     * Уникальный идентификатор записи о займе.
     * Генерируется автоматически при добавлении новой записи в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Пользователь, который взял книгу.
     * Связь многие-к-одному с сущностью User.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    /**
     * Книга, которая была взята.
     * Связь многие-к-одному с сущностью Book.
     */
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;
    /**
     * Дата, когда книга была взята.
     */
    private LocalDate borrowDate;
    /**
     * Дата, когда книга должна будет возвращена. По умолчанию устанавливается на 2 недели после даты,
     * когда книга была возвращена.
     */
    private LocalDate returnDate;
    /**
     * Дата, когда книга должна была возвращена если пользователь не вернул ее вовремя.
     */
    @Column(name = "due_date")
    private LocalDate dueDate;
    /**
     * Метод, который вызывается перед сохранением записи в базу данных.
     * Устанавливает дату займа и дату возврата по умолчанию.
     */
    @PrePersist
    protected void onCreate() {

        this.borrowDate = LocalDate.now();
        this.returnDate = LocalDate.now().plusWeeks(2);
    }


}
