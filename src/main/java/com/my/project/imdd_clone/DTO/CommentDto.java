package com.my.project.imdd_clone.DTO;

public record CommentDto(
        Long id,
        String text,
        Long userId,
        Long filmId
) {}
