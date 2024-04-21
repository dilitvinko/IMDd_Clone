package com.my.project.imdd_clone.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentDto(
        Long id,
        @NotBlank
        String text,
        Long userId,
        @NotNull
        Long filmId
) {}
