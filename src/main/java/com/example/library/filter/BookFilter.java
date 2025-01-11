package com.example.library.filter;

import com.example.library.mod.Book;
import org.springframework.data.jpa.domain.Specification;

/**
 * Класс фильтра для поиска книг по определенным критериям.
 * В данный момент поддерживает фильтрацию по идентификатору книги.
 *
 * @param id идентификатор книги, по которому будет производиться фильтрация
 */
public record BookFilter(Long id) {
    /**
     * Преобразует фильтр в спецификацию для использования с JPA Criteria API.
     *
     * @return спецификация, основанная на критериях фильтрации
     */
    public Specification<Book> toSpecification() {
        return Specification.where(idSpec());
    }

    /**
     * Создает спецификацию для фильтрации книг по идентификатору.
     *
     * @return спецификация, которая фильтрует книги по идентификатору,
     * или null, если идентификатор не задан
     */
    private Specification<Book> idSpec() {
        return ((root, query, cb) -> id != null
                ? cb.equal(root.get("id"), id) // Сравниваем идентификатор книги
                : null); // Возвращаем null, если идентификатор не задан
    }
}