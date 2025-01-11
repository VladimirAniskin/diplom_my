package com.example.library.control;

import com.example.library.dto.UserDto;
import com.example.library.exception.UserAlreadyExistsException;
import com.example.library.filter.UserFilter;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Контроллер для управления пользователями.
 * Предоставляет REST API для создания, обновления, удаления и получения информации о пользователях.
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Создает нового пользователя.
     *
     * @param dto объект передачи данных пользователя, содержащий информацию о новом пользователе
     * @return созданный объект UserDto
     * @throws UserAlreadyExistsException если пользователь с такими данными уже существует
     */
    @PostMapping("/createUaer")
    public UserDto create(@RequestBody @Valid UserDto dto) throws UserAlreadyExistsException {
        return userService.create(dto);
    }

    /**
     * Обновляет информацию о пользователе по его идентификатору.
     *
     * @param id  идентификатор пользователя, информацию которого нужно обновить
     * @param dto объект передачи данных пользователя с обновленной информацией
     * @return обновленный объект UserDto
     */
    @PutMapping("/updateUser/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        return userService.update(id, dto);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя, которого нужно удалить
     * @return объект UserDto, представляющий удаленного пользователя
     */
    @DeleteMapping("/deleteUser/{id}")
    public UserDto delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @GetMapping("/allUser")
    public PagedModel<UserDto> getList(@ParameterObject @ModelAttribute UserFilter filter, @ParameterObject Pageable pageable) {
        Page<UserDto> userDtos = userService.getList(filter, pageable);
        return new PagedModel<>(userDtos);
    }
    /**
     * Получает список пользователей с возможностью фильтрации и постраничного отображения.
     *
     * @param filter  объект фильтрации для поиска пользователей
     * @param pageable объект, содержащий информацию о постраничной навигации
     * @return PagedModel<UserDto>, содержащий список пользователей и информацию о пагинации
     */


}
