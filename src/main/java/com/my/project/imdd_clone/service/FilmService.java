package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.mapper.FilmMapper;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.repository.FilmRepository;
import com.my.project.imdd_clone.repository.RatingRepository;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final RatingRepository ratingRepository;
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

    public void updateAverageRating(Long filmId, Integer ratingPoints) {
        Film existingFilm = filmRepository.findById(filmId).orElseThrow(() -> new NoSuchElementException("Film not found with id: " + filmId));
        Integer currentRatingCount = ratingRepository.countRatingsByFilm(existingFilm);
        Double currentAverageRating = existingFilm.getAverageRating();
        Double newAverageRating = (currentAverageRating * currentRatingCount + ratingPoints)/(currentRatingCount + 1);
        existingFilm.setAverageRating(newAverageRating);
        filmRepository.save(existingFilm);
    }

    public void delete(Long id) {
        filmRepository.deleteById(id);
    }
}
