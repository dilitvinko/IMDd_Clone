package com.my.project.imdd_clone.DTO;

import java.sql.Date;
import java.util.List;

public record FilmDto(
        Long id,
        String name,
        Date releaseDate,
        String description,
        List<CommentDto> comments,
        Double averageRating
) {
}
