package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.controller.security.CustomUserDetails;
import com.my.project.imdd_clone.model.User;
import com.my.project.imdd_clone.service.RatingService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@AllArgsConstructor
public class RatingApi {

    private final RatingService ratingService;

    @PostMapping
    @RolesAllowed("USER")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto createRating(@Valid @RequestBody RatingDto ratingDto, Authentication principal) {
        User user = ((CustomUserDetails) principal.getPrincipal()).getUser();
        return ratingService.create(ratingDto, user);
    }

}

