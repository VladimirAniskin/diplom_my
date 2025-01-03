package com.example.library.filter;

import com.example.library.mod.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public record UserFilter(Long id, String name, String email, LocalDate registrationDate) {
    public Specification<User> toSpecification() {
        return Specification.where(idSpec())
                .and(nameSpec())
                .and(emailSpec())
                .and(registrationDateSpec());
    }

    private Specification<User> idSpec() {
        return ((root, query, cb) -> id != null
                ? cb.equal(root.get("id"), id)
                : null);
    }

    private Specification<User> nameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(name)
                ? cb.equal(root.get("name"), name)
                : null);
    }

    private Specification<User> emailSpec() {
        return ((root, query, cb) -> StringUtils.hasText(email)
                ? cb.equal(root.get("email"), email)
                : null);
    }

    private Specification<User> registrationDateSpec() {
        return ((root, query, cb) -> registrationDate != null
                ? cb.equal(root.get("registrationDate"), registrationDate)
                : null);
    }
}