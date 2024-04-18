package com.my.project.imdd_clone.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto {
    private Long id;
    private int points;
    private Long userId;
    private Long filmId;
}
