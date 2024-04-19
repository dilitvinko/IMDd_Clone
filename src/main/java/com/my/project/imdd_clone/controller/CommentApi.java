package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.CommentDto;
import com.my.project.imdd_clone.controller.security.CustomUserDetails;
import com.my.project.imdd_clone.service.CommentService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/film/{filmId}/comments")
@AllArgsConstructor
public class CommentApi {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getAllComments(@PathVariable Long filmId) {
        return commentService.getAllFilmComments(filmId);
    }

    @PostMapping
    @RolesAllowed("USER")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable Long filmId, @RequestBody CommentDto commentDto) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentDto.setUserId(user.user().getId());
        commentDto.setFilmId(filmId);
        return commentService.create(commentDto);
    }

}
