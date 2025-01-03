package com.example.library.filter;

import com.example.library.mod.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
/**
 * Класс фильтра для поиска пользователей по определенным критериям.
 * Поддерживает фильтрацию по идентификатору, имени, электронной почте и дате регистрации.
 *
 * @param id идентификатор пользователя, по которому будет производиться фильтрация
 * @param name имя пользователя, по которому будет производиться фильтрация
 * @param email электронная почта пользователя, по которой будет производиться фильтрация
 * @param registrationDate дата регистрации пользователя, по которой будет производиться фильтрация
 */
public record UserFilter(Long id, String name, String email, LocalDate registrationDate) {
    /**
     * Преобразует фильтр в спецификацию для использования с JPA Criteria API.
     *
     * @return спецификация, основанная на критериях фильтрации
     */
    public Specification<User> toSpecification() {
        return Specification.where(idSpec())
                .and(nameSpec())
                .and(emailSpec())
                .and(registrationDateSpec());
    }
    /**
     * Создает спецификацию для фильтрации пользователей по идентификатору.
     *
     * @return спецификация, которая фильтрует пользователей по идентификатору,
     *         или null, если идентификатор не задан
     */
    private Specification<User> idSpec() {
        return ((root, query, cb) -> id != null
                ? cb.equal(root.get("id"), id) // Сравниваем идентификатор пользователя
                : null); // Возвращаем null, если идентификатор не задан
    }
    /**
     * Создает спецификацию для фильтрации пользователей по имени.
     *
     * @return спецификация, которая фильтрует пользователей по имени,
     *         или null, если имя не задано или пустое
     */
    private Specification<User> nameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(name)
                ? cb.equal(root.get("name"), name) // Сравниваем имя пользователя
                : null); // Возвращаем null, если имя не задано или пустое
    }
    /**
     * Создает спецификацию для фильтрации пользователей по электронной почте.
     *
     * @return спецификация, которая фильтрует пользователей по электронной почте,
     *         или null, если электронная почта не задана или пустая
     */
    private Specification<User> emailSpec() {
        return ((root, query, cb) -> StringUtils.hasText(email)
                ? cb.equal(root.get("email"), email) // Сравниваем электронную почту пользователя
                : null); // Возвращаем null, если электронная почта не задана или пустая
    }
    /**
     * Создает спецификацию для фильтрации пользователей по дате регистрации.
     *
     * @return спецификация, которая фильтрует пользователей по дате регистрации,
     *         или null, если дата регистрации не задана
     */
    private Specification<User> registrationDateSpec() {
        return ((root, query, cb) -> registrationDate != null
                ? cb.equal(root.get("registrationDate"), registrationDate) // Сравниваем дату регистрации пользователя
                : null); // Возвращаем null, если дата регистрации не задана
    }
}