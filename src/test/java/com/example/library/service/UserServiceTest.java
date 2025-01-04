package com.example.library.service;
import com.example.library.dto.UserDto;
import com.example.library.exception.UserAlreadyExistsException;
import com.example.library.filter.UserFilter;
import com.example.library.maper.UserMapper;
import com.example.library.mod.User;
import com.example.library.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Тесты для UserService.
 * Проверяет логику создания, обновления, удаления и получения пользователей.
 */
public class UserServiceTest {
    private UserService userService; // Сервис, который тестирую
    private UserMapper userMapper; // Мок маппера пользователей
    private UserRepository userRepository; // Мок репозитория пользователей
    /**
     * Подготовка данных перед каждым тестом.
     * Инициализирует моки для всех зависимостей сервиса UserService.
     */
    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        userRepository = mock(UserRepository.class);
        userService = new UserService(userMapper, userRepository);
    }
    /**
     * Проверяет, что метод createUser возвращает UserDto, когда пользователь успешно создан.
     *
     * @throws UserAlreadyExistsException если пользователь с таким email уже существует
     */
    @Test
    void createUser_ShouldReturnUserDto_WhenUserIsCreated() throws UserAlreadyExistsException {
        // Arrange
        UserDto userDto = new UserDto("Victor", "test@example.com");
        userDto.getEmail();
        User user = new User();
        user.setId(1L);
        user.setEmail(userDto.getEmail());
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        UserDto result = userService.createUser (userDto);
        assertNotNull(result);
        assertEquals(userDto.getEmail(), result.getEmail());
        verify(userRepository).save(user);
    }
    /**
     * Проверяет, что метод createUser выбрасывает UserAlreadyExistsException,
     * когда пользователь с таким email уже существует.
     */
    @Test
    void createUser_ShouldThrowUserAlreadyExistsException_WhenUserWithEmailExists() {
        UserDto userDto = new UserDto("Victor", "test@example.com");
        userDto.getEmail();
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser (userDto);
        });
        assertEquals(null, exception.getMessage());
    }
    /**
     * Проверяет, что метод updateUser  выбрасывает ResponseStatusException,
     * когда пользователь не найден.
     */
    @Test
    void updateUser_ShouldThrowResponseStatusException_WhenUserNotFound() {
        Long userId = 1L;
        UserDto userDto = new UserDto("Victor", "test@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.updateUser (userId, userDto);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Пользователь с id `1` не найден", exception.getReason());
    }
    /**
     * Проверяет, что метод deleteUser  возвращает удаленного пользователя,
     * когда пользователь успешно удален.
     */
    @Test
    void deleteUser_ShouldReturnDeletedUserDto_WhenUserIsDeleted() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(new UserDto("Victor", "test@example.com"));
        UserDto result = userService.deleteUser (userId);
        assertNotNull(result);
        verify(userRepository).delete(user);
    }
    /**
     * Проверяет, что метод deleteUser возвращает null,
     * когда пользователь не найден.
     */
    @Test
    void deleteUser_ShouldReturnNull_WhenUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        UserDto result = userService.deleteUser (userId);
        assertNull(result);
        verify(userRepository, never()).delete((User) any());
    }
    /**
     * Проверяет, что метод getList возвращает страницу UserDto,
     * когда пользователи найдены.
     *
     * @throws InterruptedException если тест прерывается
     */
    @Test
    void getList_ShouldReturnPageOfUserDto_WhenUsersAreFound() throws InterruptedException {
        UserFilter filter = new UserFilter(1l, "name", "email", LocalDate.now());
        Pageable pageable = mock(Pageable.class);
        User user = new User();
        user.setId(1L);
        UserDto userDto = new UserDto("Victor", "test@example.com");
        userDto.setId(1L);
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));
        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(userPage);
        when(userMapper.toDto(user)).thenReturn(userDto);
        Page<UserDto> result = userService.getList(filter, pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userDto.getName(), result.getContent().get(0).getName());
    }
}
