package com.example.library.mod;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;


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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;
    private Integer year;
    @Column (name="number_of_copiis")
    private Integer numberOfCopies;

}
