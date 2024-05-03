package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.controller.response.APIResponse;
import com.my.project.imdd_clone.service.FilmService;
import com.querydsl.core.BooleanBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmApiUnitTest {

    @InjectMocks
    private FilmApi filmApi;

    @Mock
    private FilmService filmService;

    @Test
    void shouldGetFilm() {
        Long id = 123L;
        String name = "test";
        Date date = new Date(1);
        when(filmService.get(id)).thenReturn(new FilmDto(id, name, date, null, null, null));

        APIResponse<FilmDto> film = filmApi.getFilm(id);

        assertNotNull(film.getData());
        assertEquals(id, film.getData().id());
        assertEquals(name, film.getData().name());
        assertEquals(date, film.getData().releaseDate());
        verify(filmService).get(id);
    }

    @Test
    void shouldGetAllFilms() {
        Long id = 123L;
        String name = "test";
        Long id2 = 222L;
        String name2 = "test2";
        Date date = new Date(1);
        Date date2 = new Date(2);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        PageRequest pageRequest = PageRequest.of(0, 5);
        when(filmService.getAll(booleanBuilder, pageRequest))
                .thenReturn(new PageImpl<>(List.of(
                        new FilmDto(id, name, date, null, null, null),
                        new FilmDto(id2, name2, date2, null, null, null))));

        APIResponse<List<FilmDto>> allFilms = filmApi.getAllFilms(booleanBuilder, pageRequest);

        assertNotNull(allFilms);
        assertEquals(2, allFilms.getData().size());
        assertEquals(id, allFilms.getData().get(0).id());
        assertEquals(id2, allFilms.getData().get(1).id());
        assertEquals(name, allFilms.getData().get(0).name());
        assertEquals(date2, allFilms.getData().get(1).releaseDate());
        verify(filmService).getAll(booleanBuilder, pageRequest);
    }

    @Test
    void shouldCreateFilm() {
        Long id = 123L;
        String name = "test";
        Date date = new Date(1);
        FilmDto filmDto = new FilmDto(null, name, date, null, null, null);
        FilmDto filmDtoCreated = new FilmDto(id, name, date, null, null, null);
        when(filmService.create(filmDto))
                .thenReturn(filmDtoCreated);

        APIResponse<FilmDto> film = filmApi.createFilm(filmDto);

        assertNotNull(film);
        assertEquals(id, film.getData().id());
        assertEquals(name, film.getData().name());
        assertEquals(date, film.getData().releaseDate());
        verify(filmService).create(filmDto);
    }

    @Test
    void shouldUpdateFilm() {
        Long id = 123L;
        String name = "test";
        Date date = new Date(1);
        String description = "description";
        FilmDto filmDto = new FilmDto(null, name, date, description, null, null);
        FilmDto filmDtoUpdated = new FilmDto(id, name, date, description, null, null);
        when(filmService.update(id, filmDto))
                .thenReturn(filmDtoUpdated);

        APIResponse<FilmDto> updatedFilm = filmApi.updateFilm(id, filmDto);

        assertNotNull(updatedFilm);
        assertEquals(id, updatedFilm.getData().id());
        assertEquals(name, updatedFilm.getData().name());
        assertEquals(date, updatedFilm.getData().releaseDate());
        assertEquals(description, updatedFilm.getData().description());
        verify(filmService).update(id, filmDto);
    }

    @Test
    void shouldDeleteFilm() {
        Long id = 123L;
        doNothing().when(filmService).delete(id);

        APIResponse<Void> voidAPIResponse = filmApi.deleteFilm(id);

        assertNotNull(voidAPIResponse);
        assertNull(voidAPIResponse.getData());
        assertEquals("Film with ID " + id + " was removed", voidAPIResponse.getMessage());
        verify(filmService).delete(id);
    }
}