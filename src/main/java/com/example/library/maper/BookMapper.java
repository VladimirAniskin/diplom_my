package com.example.library.maper;

import com.example.library.dto.BookDto;
import com.example.library.mod.Book;
import org.mapstruct.*;
/**
 * Интерфейс для преобразования между сущностями книг и их DTO (Data Transfer Object).
 * Использует библиотеку MapStruct для автоматического генерации кода преобразования.
 * Также использует AuthorMapper для преобразования авторов.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthorMapper.class})
public interface BookMapper {
    /**
     * Преобразует объект BookDto в сущность Book.
     *
     * @param bookDto объект DTO книги, который необходимо преобразовать
     * @return преобразованный объект Book
     */
    Book toEntity(BookDto bookDto);
    /**
     * Преобразует сущность Book в объект BookDto.
     *
     * @param book сущность книги, которую необходимо преобразовать
     * @return преобразованный объект BookDto
     */
    BookDto toDto(Book book);
    /**
     * Обновляет существующую сущность Book, используя данные из BookDto.
     * Игнорирует свойства, которые равны null в BookDto.
     *
     * @param bookDto объект DTO книги, содержащий данные для обновления
     * @param book сущность книги, которую необходимо обновить
     * @return обновленная сущность Book
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book partialUpdate(BookDto bookDto, @MappingTarget Book book);
    /**
     * Обновляет существующую сущность Book, используя данные из BookDto.
     * В отличие от partialUpdate, этот метод обновляет свойства, даже если они равны null.
     *
     * @param bookDto объект DTO книги, содержащий данные для обновления
     * @param book сущность книги, которую необходимо обновить
     * @return обновленная сущность Book
     */
    Book updateWithNull(BookDto bookDto, @MappingTarget Book book);
}