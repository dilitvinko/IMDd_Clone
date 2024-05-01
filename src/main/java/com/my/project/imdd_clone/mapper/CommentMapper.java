package com.my.project.imdd_clone.mapper;

import com.my.project.imdd_clone.DTO.CommentDto;
import com.my.project.imdd_clone.model.Comment;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.model.User;
import com.my.project.imdd_clone.repository.FilmRepository;
import com.my.project.imdd_clone.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "filmId", source = "film.id")
    @Mapping(target = "userId", source = "user.id")
    public abstract CommentDto toDto(Comment entity);

    @Mapping(target = "film", source = "filmId")
    @Mapping(target = "user", source = "userId")
    public abstract Comment toEntity(CommentDto entity);

    Film mapFilm(Long filmId) {
        if (filmId == null) {
            return null;
        }
        return filmRepository.findById(filmId)
                .orElseThrow(() -> new NoSuchElementException("Film not found with id: " + filmId));
    }

    User mapUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }
}
