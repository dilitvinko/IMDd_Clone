package com.my.project.imdd_clone.clients;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.model.Film;
import com.querydsl.core.types.Predicate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "films", url = "${test.url}")
public interface FilmClient {

    @GetMapping("/films/{id}")
    FilmDto getFilm(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @GetMapping("/films")
    Page<FilmDto> getAllFilms(@RequestHeader("Authorization") String token);

    @PostMapping("/films")
    FilmDto createFilm(@RequestBody FilmDto filmDto, @RequestHeader("Authorization") String token);

    @PutMapping("/films/{id}")
    FilmDto updateFilm(@PathVariable("id") Long id, @RequestBody FilmDto filmDto, @RequestHeader("Authorization") String token);

    @DeleteMapping("/films/{id}")
    void deleteFilm(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);
}