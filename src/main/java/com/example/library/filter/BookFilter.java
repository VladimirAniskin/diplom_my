package com.example.library.filter;

import com.example.library.mod.Book;
import org.springframework.data.jpa.domain.Specification;

public record BookFilter(Long id) {
    public Specification<Book> toSpecification() {
        return Specification.where(idSpec());
    }

    private Specification<Book> idSpec() {
        return ((root, query, cb) -> id != null
                ? cb.equal(root.get("id"), id)
                : null);
    }
}