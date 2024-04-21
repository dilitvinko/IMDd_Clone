package com.my.project.imdd_clone.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingDto(
        Long id,
        @Min(1)
        @Max(10)
        Integer points,
        Long userId,
        @NotNull
        Long filmId
) {
}
