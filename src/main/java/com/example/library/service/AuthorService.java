package com.example.library.service;

import com.example.library.dto.AuthorDto;
import com.example.library.exception.UserAlreadyExistsException;
import com.example.library.maper.AuthorMapper;
import com.example.library.mod.Author;
import com.example.library.repo.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorMapper authorMapper;

    private final AuthorRepository authorRepository;

    public AuthorDto create(AuthorDto dto) throws UserAlreadyExistsException {
        if (authorRepository.existsByName(dto.getName())) {
            throw new UserAlreadyExistsException("Автор с таким именем существует.");
        }
        Author author = authorMapper.toEntity(dto);
        Author resultAuthor = authorRepository.save(author);
        return authorMapper.toDto(resultAuthor);
    }

    public AuthorDto update(Long id, AuthorDto dto) {
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        authorMapper.updateWithNull(dto, author);
        Author resultAuthor = authorRepository.save(author);
        return authorMapper.toDto(resultAuthor);
    }

    public AuthorDto delete(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            authorRepository.delete(author);
        }
        return authorMapper.toDto(author);
    }

    public AuthorDto getOne(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        return authorMapper.toDto(authorOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }
}
