package com.example.library.maper;

import com.example.library.dto.BookDto;
import com.example.library.mod.Book;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
    Book toEntity ( BookDto bookDto );

    BookDto toDto ( Book book );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book partialUpdate ( BookDto bookDto, @MappingTarget Book book );

    Book updateWithNull ( BookDto bookDto, @MappingTarget Book book );
}