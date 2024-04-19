package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.mapper.RatingMapper;
import com.my.project.imdd_clone.model.Rating;
import com.my.project.imdd_clone.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    public RatingDto create(RatingDto ratingDto) {
        Rating rating = ratingMapper.toEntity(ratingDto);
        rating = ratingRepository.save(rating);
        return ratingMapper.toDto(rating);
    }
}
