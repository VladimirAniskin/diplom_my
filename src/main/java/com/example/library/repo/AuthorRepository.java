package com.example.library.repo;

import com.example.library.mod.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Интерфейс AuthorRepository, предоставляющий методы для работы с сущностью Author.
 * Расширяет JpaRepository для использования стандартных операций CRUD.
 */
@Repository
public interface AuthorRepository  extends JpaRepository<Author, Long> {
    /**
     * Проверяет, существует ли автор с указанным именем.
     *
     * @param name имя автора
     * @return true, если автор с таким именем существует, иначе false
     */
    boolean existsByName(String name);
    /**
     * Находит автора по стране.
     *
     * @param country страна автора
     * @return объект Author, если автор с указанной страной найден, иначе null
     */
    Author findByCountry(String country);
}
