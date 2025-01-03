package com.example.library.maper;

import com.example.library.dto.AuthorDto;
import com.example.library.mod.Author;
import org.mapstruct.*;
/**
 * Интерфейс для преобразования между сущностями авторов и их DTO (Data Transfer Object).
 * Использует библиотеку MapStruct для автоматического генерации кода преобразования.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {
    /**
     * Преобразует объект AuthorDto в сущность Author.
     *
     * @param authorDto объект DTO автора, который необходимо преобразовать
     * @return преобразованный объект Author
     */
    Author toEntity(AuthorDto authorDto);
    /**
     * Преобразует сущность Author в объект AuthorDto.
     *
     * @param author сущность автора, которую необходимо преобразовать
     * @return преобразованный объект AuthorDto
     */
    AuthorDto toDto(Author author);
    /**
     * Обновляет существующую сущность Author, используя данные из AuthorDto.
     * Игнорирует свойства, которые равны null в AuthorDto.
     *
     * @param authorDto объект DTO автора, содержащий данные для обновления
     * @param author сущность автора, которую необходимо обновить
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Author partialUpdate(AuthorDto authorDto, @MappingTarget Author author);
    /**
     * Обновляет существующую сущность Author, используя данные из AuthorDto.
     * В отличие от partialUpdate, этот метод обновляет свойства, даже если они равны null.
     *
     * @param authorDto объект DTO автора, содержащий данные для обновления
     * @param author сущность автора, которую необходимо обновить
     * @return обновленная сущность Author
     */
    Author updateWithNull(AuthorDto authorDto, @MappingTarget Author author);
}