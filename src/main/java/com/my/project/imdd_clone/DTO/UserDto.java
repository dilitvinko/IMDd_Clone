package com.my.project.imdd_clone.DTO;

import java.util.Set;

public record UserDto(
         Long id,
         String name,
         String email,
         String username,
         String password,
//     List<Comment> comments;
//     List<Rating> ratings;
         Set<RoleDto> roles
) {
}
