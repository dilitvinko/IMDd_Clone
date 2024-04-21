package com.my.project.imdd_clone.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserDto(
         Long id,
         @NotBlank
         String name,
         @Email
         String email,
         @NotBlank
         String username,
         @NotBlank
         String password,
//     List<Comment> comments;
//     List<Rating> ratings;
         Set<RoleDto> roles
) {
}
