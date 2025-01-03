package com.example.library.maper;

import com.example.library.dto.UserDto;
import com.example.library.mod.User;
import org.mapstruct.*;
/**
 * Интерфейс для преобразования между сущностями пользователей и их DTO (Data Transfer Object).
 * Использует библиотеку MapStruct для автоматического генерации кода преобразования.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    /**
     * Преобразует объект UserDto в сущность User.
     *
     * @param userDto объект DTO пользователя, который необходимо преобразовать
     * @return преобразованный объект User
     */
    User toEntity(UserDto userDto);
    /**
     * Преобразует сущность User в объект UserDto.
     *
     * @param user сущность пользователя, которую необходимо преобразовать
     * @return преобразованный объект UserDto
     */
    UserDto toDto(User user);
    /**
     * Обновляет существующую сущность User, используя данные из UserDto.
     * Игнорирует свойства, которые равны null в UserDto.
     *
     * @param userDto объект DTO пользователя, содержащий данные для обновления
     * @param user сущность пользователя, которую необходимо обновить
     * @return обновленная сущность User
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
    /**
     * Обновляет существующую сущность User, используя данные из UserDto.
     * В отличие от partialUpdate, этот метод обновляет свойства, даже если они равны null.
     *
     * @param userDto объект DTO пользователя, содержащий данные для обновления
     * @param user сущность пользователя, которую необходимо обновить
     * @return обновленная сущность User
     */
    User updateWithNull(UserDto userDto, @MappingTarget User user);
}