package com.my.project.imdd_clone.DTO;

import jakarta.validation.constraints.NotBlank;

import java.sql.Date;
import java.util.List;

public record FilmDto(
        Long id,
        @NotBlank
        String name,
        @NotBlank
        Date releaseDate,
        String description,
        List<CommentDto> comments,
        Double averageRating
) {
}
