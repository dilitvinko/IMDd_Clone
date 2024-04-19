package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.controller.security.CustomUserDetails;
import com.my.project.imdd_clone.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@AllArgsConstructor
public class RatingApi {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto createRating(@RequestBody RatingDto ratingDto) {
        CustomUserDetails user = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ratingDto.setUserId(user.user().getId());
        return ratingService.create(ratingDto);
    }

}

