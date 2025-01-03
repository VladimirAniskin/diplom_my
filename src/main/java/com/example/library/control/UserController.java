package com.example.library.control;
import com.example.library.dto.UserDto;
import com.example.library.exception.UserAlreadyExistsException;
import com.example.library.filter.UserFilter;
import com.example.library.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@Tag(name = "User", description = "User API")
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    @RequestMapping (value = "/createUser", method = RequestMethod.POST)
    public UserDto createUser(@RequestBody @Valid UserDto dto) throws UserAlreadyExistsException {
        return userService.createUser(dto);
    }

    @RequestMapping(value = "/updateUser/ {id}" , method = RequestMethod.PUT)
    public UserDto updateUser(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        return userService.updateUser(id, dto);
    }


    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public UserDto deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }


    @RequestMapping(value = "/allUser", method = RequestMethod.GET)
    public PagedModel<UserDto> getList(@ParameterObject @ModelAttribute UserFilter filter, @ParameterObject Pageable pageable) {
        Page<UserDto> userDtos = userService.getList(filter, pageable);
        return new PagedModel<>(userDtos);
    }

}
