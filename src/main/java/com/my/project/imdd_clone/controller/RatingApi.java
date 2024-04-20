package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.controller.security.CustomUserDetails;
import com.my.project.imdd_clone.model.User;
import com.my.project.imdd_clone.service.RatingService;
import com.my.project.imdd_clone.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/ratings")
@AllArgsConstructor
public class RatingApi {

    private final RatingService ratingService;
    private final UserService userService;

    @PostMapping
    @RolesAllowed("USER")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto createRating(@RequestBody RatingDto ratingDto, Authentication principal) {
        User user = ((CustomUserDetails) principal.getPrincipal()).getUser();
        return ratingService.create(ratingDto, user);
    }

}

