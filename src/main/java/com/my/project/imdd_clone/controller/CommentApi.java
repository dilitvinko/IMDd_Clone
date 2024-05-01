package com.my.project.imdd_clone.controller;

import java.util.List;

import com.my.project.imdd_clone.controller.response.APIResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.my.project.imdd_clone.DTO.CommentDto;
import com.my.project.imdd_clone.service.CommentService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentApi {

    private final CommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public APIResponse<List<CommentDto>> getAllFilmComments(@RequestParam Long filmId, @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        List<CommentDto> result;
        // logic only for tests
        try {
            result = commentService.getAllFilmComments(filmId, pageable);
        } catch (RuntimeException e) {
            return APIResponse.<List<CommentDto>>builder()
                    .data(List.of())
                    .build();
        }
        return APIResponse.<List<CommentDto>>builder()
                .data(result)
                .build();
    }

    @PostMapping
    @RolesAllowed("USER")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@Valid @RequestBody CommentDto commentDto) {
        var result = commentService.create(commentDto);
        // logic only for tests
        if (StringUtils.isEmpty(result.text()) ){
            throw new IllegalStateException("empty text was saved");
        }
        return result;
    }

}
