package com.my.project.imdd_clone.mapper;

import com.my.project.imdd_clone.DTO.CommentDto;
import com.my.project.imdd_clone.model.Comment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "filmId", source = "film.id")
    @Mapping(target = "userId", source = "user.id")
    CommentDto toDto(Comment entity);

    Comment toEntity(CommentDto entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartial(@MappingTarget Comment entity, CommentDto dto);
}
