package com.example.library.service;
import com.example.library.dto.UserDto;
import com.example.library.exception.UserAlreadyExistsException;
import com.example.library.maper.UserMapper;
import com.example.library.mod.User;
import com.example.library.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    private UserDto userDto;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = new UserDto("Test User", "test@example.com");
        user = new User();
        user.setEmail("test@example.com");
    }


    @Test
    public void testCreate_Success() throws UserAlreadyExistsException {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto createdUserDto = userService.create(userDto);

        assertNotNull(createdUserDto);
        assertEquals(userDto.getEmail(), createdUserDto.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void update_UserNotFound_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.update(1L, userDto);
        });

        assertEquals("пользователь с id `1` не найден", exception.getReason());
    }

    @Test
    void delete_UserExists_ReturnsDeletedUserDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto deletedUserDto = userService.delete(1L);

        assertNotNull(deletedUserDto);
        assertEquals(userDto.getEmail(), deletedUserDto.getEmail());
        verify(userRepository).delete(user);
    }

    @Test
    void delete_UserNotFound_ReturnsNull() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserDto deletedUserDto = userService.delete(1L);

        assertNull(deletedUserDto);
        verify(userRepository, never()).delete((User) any());
    }

}


