package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.mapper.RatingMapper;
import com.my.project.imdd_clone.model.Rating;
import com.my.project.imdd_clone.repository.RatingRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final FilmService filmService;

    @Transactional
    public RatingDto create(@Valid RatingDto ratingDto) {
        Rating rating = ratingMapper.toEntity(ratingDto);
        filmService.updateAverageRating(rating.getFilm().getId(), rating.getPoints());
        rating = ratingRepository.save(rating);
        return ratingMapper.toDto(rating);
    }
}
