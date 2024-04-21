package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.mapper.RatingMapper;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.repository.RatingRepository;
import com.my.project.imdd_clone.service.FilmService;
import com.querydsl.core.types.Predicate;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmApi {

    private final FilmService filmService;

    @GetMapping("/{id}")
    public FilmDto getFilm(@PathVariable("id") Long id) {
        return filmService.get(id);
    }

    @GetMapping
    public Page<FilmDto> getAllFilms(
            @QuerydslPredicate(root = Film.class) Predicate predicate,
            @PageableDefault(size = 5, sort = "id") Pageable pageable
    ) {
        return filmService.getAll(predicate, pageable);
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDto createFilm(@Valid @RequestBody FilmDto filmDto) {
        return filmService.create(filmDto);
    }

    @PutMapping("/{id}")
    @RolesAllowed("ADMIN")
    public FilmDto updateFilm(@PathVariable("id") Long id, @RequestBody FilmDto filmDto) {
        return filmService.update(id, filmDto);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable("id") Long id) {
        filmService.delete(id);
    }

}
