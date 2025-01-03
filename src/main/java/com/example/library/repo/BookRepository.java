package com.example.library.repo;

import com.example.library.mod.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
/**
 * Интерфейс BookRepository, предоставляющий методы для работы с сущностью Book.
 * Расширяет JpaRepository для использования стандартных операций CRUD
 * и JpaSpecificationExecutor для поддержки спецификаций.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    /**
     * Проверяет, существует ли книга с указанным именем.
     *
     * @param name имя книги
     * @return true, если книга с таким именем существует, иначе false
     */
    boolean existsByName(String name);
}
