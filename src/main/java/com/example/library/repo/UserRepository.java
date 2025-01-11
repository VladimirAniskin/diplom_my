package com.example.library.repo;

import com.example.library.mod.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс UserRepository, предоставляющий методы для работы с сущностью User.
 * Расширяет JpaRepository для использования стандартных операций CRUD
 * и JpaSpecificationExecutor для поддержки спецификаций.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {


    boolean existsByEmail(String email);
}
