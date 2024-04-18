package com.my.project.imdd_clone.mapper;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.repository.RatingRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public abstract class FilmMapper {

    @Autowired
    RatingRepository ratingRepository;

    @Mapping(target = "rating", expression = "java(getRating(entity.getId()))")
    public abstract FilmDto toDto(Film entity);

    public abstract Film toEntity(FilmDto entity);

    Double getRating(Long filmId) {
        return ratingRepository.getFilmAverageRating(filmId);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract void updatePartial(@MappingTarget Film entity, FilmDto dto);
}
