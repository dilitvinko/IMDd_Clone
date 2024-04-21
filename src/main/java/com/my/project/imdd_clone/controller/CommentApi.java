package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.CommentDto;
import com.my.project.imdd_clone.controller.security.CustomUserDetails;
import com.my.project.imdd_clone.model.User;
import com.my.project.imdd_clone.service.CommentService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentApi {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getAllComments(@RequestParam Long filmId) {
        return commentService.getAllFilmComments(filmId);
    }

    @PostMapping
    @RolesAllowed("USER")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@Valid @RequestBody CommentDto commentDto, Authentication principal) {
        User user = ((CustomUserDetails) principal.getPrincipal()).getUser();
        return commentService.create(commentDto, user);
    }

}
