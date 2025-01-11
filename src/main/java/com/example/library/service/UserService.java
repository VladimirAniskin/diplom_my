package com.example.library.service;

import com.example.library.dto.UserDto;
import com.example.library.exception.UserAlreadyExistsException;
import com.example.library.filter.UserFilter;
import com.example.library.maper.UserMapper;
import com.example.library.mod.User;
import com.example.library.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Сервис для управления пользователями.
 * Предоставляет методы для создания, обновления, удаления и получения списка пользователей.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    /**
     * Создает нового пользователя.
     *
     * @param dto объект UserDto, содержащий информацию о пользователе
     * @return UserDto созданного пользователя
     */
    public UserDto create(UserDto dto) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с таким email уже существует.");
        }
        User user = userMapper.toEntity(dto);
        User resultUser = userRepository.save(user);
        return userMapper.toDto(resultUser);
    }

    /**
     * Обновляет информацию о пользователе.
     *
     * @param id  идентификатор пользователя
     * @param dto объект UserDto с новыми данными
     * @return UserDto обновленного пользователя
     * @throws ResponseStatusException если пользователь не найден
     */

    public UserDto update(Long id, UserDto dto) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "пользователь с id `%s` не найден".formatted(id)));
        userMapper.updateWithNull(dto, user);
        User resultUser = userRepository.save(user);
        return userMapper.toDto(resultUser);
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return UserDto удаленного пользователя или null, если пользователь не найден
     */

    public UserDto delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        return userMapper.toDto(user);
    }

    /**
     * Получает список пользователей с применением фильтрации и пагинации.
     *
     * @param filter   фильтр для поиска пользователей
     * @param pageable параметры пагинации
     * @return Page<UserDto> страница пользователей
     */

    public Page<UserDto> getList(UserFilter filter, Pageable pageable) {
        Specification<User> spec = filter.toSpecification();
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::toDto);
    }

}
