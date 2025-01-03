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


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public UserDto createUser(UserDto dto) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с таким email уже существует.");
        }
        User user = userMapper.toEntity(dto);
        User resultUser = userRepository.save(user);
        return userMapper.toDto(resultUser);
    }

    public UserDto updateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        userMapper.updateWithNull(dto, user);
        User resultUser = userRepository.save(user);
        return userMapper.toDto(resultUser);
    }

    public UserDto deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        return userMapper.toDto(user);
    }

    public Page<UserDto> getList(UserFilter filter, Pageable pageable) {
        Specification<User> spec = filter.toSpecification();
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::toDto);
    }

}
