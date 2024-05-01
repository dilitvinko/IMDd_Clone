package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.controller.response.APIResponse;
import com.my.project.imdd_clone.service.RatingService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@AllArgsConstructor
public class RatingApi {

    private final RatingService ratingService;

    @PostMapping
    @RolesAllowed("USER")
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<RatingDto> createRating(@Valid @RequestBody RatingDto ratingDto) {
        RatingDto ratingDtoCreated = ratingService.create(ratingDto);
        return APIResponse.<RatingDto>builder()
                .data(ratingDtoCreated)
                .build();
    }

}

