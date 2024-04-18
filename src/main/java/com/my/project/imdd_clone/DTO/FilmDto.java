package com.my.project.imdd_clone.DTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class FilmDto {
    private Long id;
    private String name;
    private Date releaseDate;
    private String description;
    private List<CommentDto> comments;
    private Double rating;
}
