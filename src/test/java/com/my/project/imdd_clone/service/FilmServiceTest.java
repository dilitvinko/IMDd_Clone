package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.mapper.FilmMapper;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.repository.FilmRepository;
import com.my.project.imdd_clone.repository.RatingRepository;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @InjectMocks
    private FilmService filmService;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Spy
    private FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

    @Test
    void shouldGetFilm() {
        Long filmId = 111L;
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(new Film(filmId, "test name", null, null, null, null, null)));

        FilmDto result = filmService.get(filmId);

        assertNotNull(result);
        assertEquals(filmId, result.id());
        verify(filmRepository).findById(filmId);
    }

    @Test
    void shouldThrowExceptionIfFilmNotFound() {
        Long filmId = 222L;
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> filmService.get(filmId),
                "Film not found with id: " + filmId
        );

        verify(filmRepository).findById(filmId);
    }

    @Test
    void getAll() {
        BooleanBuilder predicate = new BooleanBuilder();
        PageRequest of = PageRequest.of(0, 5);
        when(filmRepository.findAll(predicate, of)).thenReturn(new PageImpl<>(List.of()));

        Page<FilmDto> result = filmService.getAll(predicate, of);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        verify(filmRepository).findAll(predicate, of);
    }

    @Test
    void shouldCreate() {
        Long filmId = 12345L;
        Film film = new Film();
        film.setId(filmId);
        Date releaseDate = new Date(1);
        film.setReleaseDate(releaseDate);
        FilmDto test_film = new FilmDto(filmId, "test Film", releaseDate, null, null, null);
        when(filmMapper.toEntity(test_film)).thenReturn(film);
        when(filmRepository.save(film)).thenReturn(film);

        FilmDto result = filmService.create(test_film);

        assertNotNull(result);
        assertEquals(filmId, result.id());
        verify(filmRepository).save(film);
    }

    @Test
    void tryToUpdateAndThrowExceptionWhenFilmDoesntExist() {
        Long filmId = 12345L;
        Film film = new Film();
        film.setId(filmId);
        FilmDto test_film = new FilmDto(filmId, "test Film", new Date(1), null, null, null);
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> filmService.update(filmId, test_film),
                "Film not found with id: " + filmId
        );
        verify(filmRepository).findById(filmId);
    }

    @Test
    void shouldUpdate() {
        Long filmId = 12345L;
        Film film = new Film();
        film.setId(filmId);
        FilmDto test_film = new FilmDto(filmId, "test Film", new Date(1), null, null, null);
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));
        when(filmRepository.save(film)).thenReturn(film);

        FilmDto result = filmService.update(filmId, test_film);

        assertNotNull(result);
        assertEquals(filmId, result.id());
        verify(filmRepository).findById(filmId);
        verify(filmRepository).save(film);
    }

    @Test
    void tryToUpdateAverageRatingAndThrowExceptionWhenFilmDoesntExist() {
        Long filmId = 12345L;
        Integer ratingPoints = 5;
        Film film = new Film();
        film.setId(filmId);
        film.setAverageRating(5.5);
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> filmService.updateAverageRating(filmId, ratingPoints),
                "Film not found with id: " + filmId
        );
        verify(filmRepository).findById(filmId);
    }

    @Test
    void shouldUpdateAverageRatingWhenFilmHasNullRatingInDB() {
        Long filmId = 12345L;
        Integer ratingPoints = 5;
        Film film = new Film();
        film.setId(filmId);
        film.setAverageRating(null);
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));
        when(ratingRepository.countRatingsByFilm(film)).thenReturn(Optional.empty());
        Double expectedNewAverageRating = (double) ((0 * 0 + ratingPoints) / (0 + 1));

        filmService.updateAverageRating(filmId, ratingPoints);

        assertEquals(expectedNewAverageRating, film.getAverageRating());
        verify(filmRepository).findById(filmId);
        verify(ratingRepository).countRatingsByFilm(film);
        verify(filmRepository).save(film);
    }

    @Test
    void shouldUpdateAverageRatingWhenRatingsDontExistsForThisFilm() {
        Long filmId = 12345L;
        Integer ratingPoints = 5;
        Film film = new Film();
        film.setId(filmId);
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));
        when(ratingRepository.countRatingsByFilm(film)).thenReturn(Optional.empty());
        Double expectedNewAverageRating = (double) ((0 * 0 + ratingPoints) / (0 + 1));

        filmService.updateAverageRating(filmId, ratingPoints);

        assertEquals(expectedNewAverageRating, film.getAverageRating());
        verify(filmRepository).findById(filmId);
        verify(ratingRepository).countRatingsByFilm(film);
        verify(filmRepository).save(film);
    }

    @Test
    void shouldUpdateAverageRating() {
        Long filmId = 12345L;
        Integer ratingPoints = 10;
        Double prevFilmRating = 7.5;
        Integer currentRatingCount = 3;
        Film film = new Film();
        film.setId(filmId);
        film.setAverageRating(prevFilmRating);
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));
        when(ratingRepository.countRatingsByFilm(film)).thenReturn(Optional.of(currentRatingCount));
        Double expectedNewAverageRating = (prevFilmRating * currentRatingCount + ratingPoints) / (currentRatingCount + 1);

        filmService.updateAverageRating(filmId, ratingPoints);

        assertEquals(expectedNewAverageRating, film.getAverageRating());
        verify(filmRepository).findById(filmId);
        verify(ratingRepository).countRatingsByFilm(film);
        verify(filmRepository).save(film);
    }


    @Test
    void delete() {
        Long filmId = 12345L;

        filmService.delete(filmId);

        verify(filmRepository).deleteById(filmId);
    }
}