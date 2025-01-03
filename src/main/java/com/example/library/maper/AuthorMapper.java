package com.example.library.maper;

import com.example.library.dto.AuthorDto;
import com.example.library.mod.Author;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {
    Author toEntity(AuthorDto authorDto);

    AuthorDto toDto(Author author);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Author partialUpdate(AuthorDto authorDto, @MappingTarget Author author);

    Author updateWithNull(AuthorDto authorDto, @MappingTarget Author author);
}