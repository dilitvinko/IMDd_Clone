package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.mapper.FilmMapper;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.repository.FilmRepository;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@AllArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    public FilmDto get(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Film not found with id: " + id));
        return filmMapper.toDto(film);
    }

    public Page<FilmDto> getAll(Predicate predicate, Pageable pageable) {
        Page<Film> films = filmRepository.findAll(predicate, pageable);
        return films.map(filmMapper::toDto);
    }

    public FilmDto create(FilmDto filmDto) {
        Film film = filmMapper.toEntity(filmDto);
        film = filmRepository.save(film);
        return filmMapper.toDto(film);
    }

    public FilmDto update(Long id, FilmDto filmDto) {
        Film existingFilm = filmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Film not found with id: " + id));
        Film updatedFilm = filmMapper.toEntity(filmDto);
        updatedFilm.setId(existingFilm.getId());
        updatedFilm = filmRepository.save(updatedFilm);
        return filmMapper.toDto(updatedFilm);
    }

    public void delete(Long id) {
        filmRepository.deleteById(id);
    }
}
