package com.my.project.imdd_clone.DTO;

public record RatingDto(
        Long id,
        int points,
        Long userId,
        Long filmId
) {
}
