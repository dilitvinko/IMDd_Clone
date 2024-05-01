package com.my.project.imdd_clone.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.my.project.imdd_clone.DTO.CommentDto;
import com.my.project.imdd_clone.service.CommentService;

@ExtendWith(MockitoExtension.class)
class CommentApiTest1 {

    @InjectMocks
    private CommentApi sut;

    @Mock
    private CommentService commentService;

    @Test
    void shouldReturnAllElements() {
        // preconditions:
        var filmId = 32589L;
        var userId = 2453456L;
        var text = "text_1";
        var id = 5645L;
        when(commentService.getAllFilmComments(filmId, null))
            .thenReturn(List.of(commentDto(id, text, userId, filmId)));

        // logic:
        var result = sut.getAllFilmComments(filmId, null);

        // verify:
        assertEquals(1, result.getData().size());
        assertEquals(id, result.getData().get(0).id());
        assertEquals(filmId, result.getData().get(0).filmId());
        assertEquals(userId, result.getData().get(0).userId());
        assertEquals(text, result.getData().get(0).text());
        verify(commentService).getAllFilmComments(filmId, null);
    }

    @Test
    void shouldReturnEmptyListInCaseOfException() {
        // preconditions:
        var filmId = 436564L;
        when(commentService.getAllFilmComments(filmId, null))
            .thenThrow(new IllegalStateException());

        // logic:
        var result = sut.getAllFilmComments(filmId, null);

        // verify:
        assertEquals(0, result.getData().size());
        verify(commentService).getAllFilmComments(filmId, null);
    }

    @Test
    void shouldReturnIllegalStateExceptionWhenTextIsEmpty() {
        // preconditions
        var filmId = 54645L;
        var userId = 8678L;
        var id = 45645L;
        when(commentService.create(commentDto(id, null, userId, filmId)))
            .thenReturn(commentDto(id, null, userId, filmId));

        // logic:
        var exc = assertThrows(IllegalStateException.class,
            () -> sut.createComment(commentDto(id, null, userId, filmId)));

        // verify:
        verify(commentService).create(commentDto(id, null, userId, filmId));
        assertTrue(exc.getMessage().contains("empty text"));
    }

    private CommentDto commentDto(Long id, String text, Long userId, Long filmId) {
        return new CommentDto(id, text, userId, filmId);
    }


}