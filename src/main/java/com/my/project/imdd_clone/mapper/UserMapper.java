package com.my.project.imdd_clone.mapper;

import com.my.project.imdd_clone.DTO.UserDto;
import com.my.project.imdd_clone.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User entity);

    User toEntity(UserDto entity);
}
