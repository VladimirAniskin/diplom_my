package com.example.library.mod;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BorrowRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    @Column(name = "due_date")
    private LocalDate dueDate;

    @PrePersist
    protected void onCreate() {

        this.borrowDate = LocalDate.now();
        this.returnDate = LocalDate.now().plusWeeks(2);
    }


}
