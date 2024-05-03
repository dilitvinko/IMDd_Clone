package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.controller.response.APIResponse;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.service.FilmService;
import com.querydsl.core.types.Predicate;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmApi {

    private final FilmService filmService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse<FilmDto> getFilm(@PathVariable("id") Long id) {
        FilmDto filmDto = filmService.get(id);
        return APIResponse.<FilmDto>builder()
                .data(filmDto)
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public APIResponse<List<FilmDto>> getAllFilms(
            @QuerydslPredicate(root = Film.class) Predicate predicate,
            @PageableDefault(size = 5, sort = "id") Pageable pageable
    ) {
        List<FilmDto> films = filmService.getAll(predicate, pageable).getContent();
        return APIResponse.<List<FilmDto>>builder()
                .data(films)
                .build();
    }

    // TODO implement CriteriaApi

    @PostMapping
    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<FilmDto> createFilm(@Valid @RequestBody FilmDto filmDto) {
        FilmDto filmDtoCreated = filmService.create(filmDto);
        return APIResponse.<FilmDto>builder()
                .data(filmDtoCreated)
                .build();
    }

    @PutMapping("/{id}")
    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse<FilmDto> updateFilm(@PathVariable("id") Long id, @RequestBody FilmDto filmDto) {
        FilmDto filmUpdated = filmService.update(id, filmDto);
        return APIResponse.<FilmDto>builder()
                .data(filmUpdated)
                .build();
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse<Void> deleteFilm(@PathVariable("id") Long id) {
        filmService.delete(id);
        return APIResponse.<Void>builder()
                .data(null)
                .message("Film with ID " + id + " was removed")
                .build();
    }

}
