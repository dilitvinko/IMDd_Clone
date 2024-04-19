package com.my.project.imdd_clone.mapper;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.model.Rating;
import com.my.project.imdd_clone.model.User;
import com.my.project.imdd_clone.repository.FilmRepository;
import com.my.project.imdd_clone.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RatingMapper {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    UserRepository userRepository;

    @Mapping(target = "filmId", source = "film.id")
    @Mapping(target = "userId", source = "user.id")
    public abstract RatingDto toDto(Rating entity);

    @Mapping(target = "film", source = "filmId")
    @Mapping(target = "user", source = "userId")
    public abstract Rating toEntity(RatingDto entity);

    Film mapFilm(Long filmId) {
        if (filmId == null) {
            return null;
        }
        return filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("Film not found with id: " + filmId));
    }

    User mapUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract void updatePartial(@MappingTarget Rating entity, RatingDto dto);
}
