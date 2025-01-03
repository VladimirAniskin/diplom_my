package com.example.library.mod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import java.time.LocalDate;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @Email(message = "В почте отсутствует @ или .ru ")
    private String email;
    @Column(name = "registration_date", updatable = false)
    private LocalDate registrationDate;

    @PrePersist
    protected void onCreate() {

        this.registrationDate = LocalDate.now();
    }



}


