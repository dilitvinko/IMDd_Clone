package com.my.project.imdd_clone.mapper;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.model.Rating;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {FilmMapper.class, UserMapper.class})
public interface RatingMapper {

    @Mapping(target = "filmId", source = "film.id")
    @Mapping(target = "userId", source = "user.id")
    RatingDto toDto(Rating entity);

    Rating toEntity(RatingDto entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartial(@MappingTarget Rating entity, RatingDto dto);
}
