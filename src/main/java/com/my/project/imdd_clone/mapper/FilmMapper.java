package com.my.project.imdd_clone.mapper;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.repository.RatingRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public abstract class FilmMapper {

    public abstract FilmDto toDto(Film entity);

    @Mapping(target = "averageRating", ignore = true)
    public abstract Film toEntity(FilmDto entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "averageRating", ignore = true)
    public abstract void updatePartial(@MappingTarget Film entity, FilmDto dto);
}
