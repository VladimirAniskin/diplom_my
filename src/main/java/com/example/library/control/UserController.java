package com.example.library.control;
import com.example.library.dto.UserDto;
import com.example.library.exception.UserAlreadyExistsException;
import com.example.library.filter.UserFilter;
import com.example.library.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import javax.validation.Valid;


/**
 * Контроллер для управления пользователями.
 * Предоставляет REST API для создания, обновления, удаления и получения информации о пользователях.
 */
@RestController
@Tag(name = "User", description = "User API")
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    /**
     * Создает нового пользователя.
     *
     * @param dto объект передачи данных пользователя, содержащий информацию о новом пользователе
     * @return созданный объект UserDto
     * @throws UserAlreadyExistsException если пользователь с такими данными уже существует
     */

    @RequestMapping (value = "/createUser", method = RequestMethod.POST)
    public UserDto createUser(@RequestBody @Valid UserDto dto) throws UserAlreadyExistsException {
        return userService.createUser(dto);
    }
    /**
     * Обновляет информацию о пользователе по его идентификатору.
     *
     * @param id  идентификатор пользователя, информацию которого нужно обновить
     * @param dto объект передачи данных пользователя с обновленной информацией
     * @return обновленный объект UserDto
     */
    @RequestMapping(value = "/updateUser/ {id}" , method = RequestMethod.PUT)
    public UserDto updateUser(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        return userService.updateUser(id, dto);
    }
    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя, которого нужно удалить
     * @return объект UserDto, представляющий удаленного пользователя
     */

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public UserDto deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
    /**
     * Получает список пользователей с возможностью фильтрации и постраничного отображения.
     *
     * @param filter  объект фильтрации для поиска пользователей
     * @param pageable объект, содержащий информацию о постраничной навигации
     * @return PagedModel<UserDto>, содержащий список пользователей и информацию о пагинации
     */

    @RequestMapping(value = "/allUser", method = RequestMethod.GET)
    public PagedModel<UserDto> getList(@ParameterObject @ModelAttribute UserFilter filter, @ParameterObject Pageable pageable) {
        Page<UserDto> userDtos = userService.getList(filter, pageable);
        return new PagedModel<>(userDtos);
    }
}
