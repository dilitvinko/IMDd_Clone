package com.my.project.imdd_clone.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.controller.response.APIResponse;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class FilmApiComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.6")
            .withUsername("postgresTest")
            .withPassword("postgresTest")
            .withReuse(Boolean.TRUE);

    @BeforeEach
    public void initData() {
        filmRepository.deleteAll();
    }

    @Test
    @WithMockUser()
    void shouldThrowNoSuchElementExceptionWhenFilmDoesntExist() throws Exception {
        Long id = 1L;

        mockMvc.perform(get("/films/{id}", id))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException))
                .andExpect(jsonPath("$.message").value("Film not found with id: " + id));
    }

    @Test
    void shouldThrowUnauthorizedForNoAuth() throws Exception {
        String name = "Test Name";
        Date date = new Date(1);

        Film savedFilm = filmRepository.save(new Film(null, name, date, null, null, null, null));

        mockMvc.perform(get("/films/{id}", savedFilm.getId()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    void shouldGetFilm() throws Exception {
        String name = "Test Name";
        Date date = new Date(1);

        Film savedFilm = filmRepository.save(new Film(null, name, date, null, null, null, null));

        mockMvc.perform(get("/films/{id}", savedFilm.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(savedFilm.getId()))
                .andExpect(jsonPath("$.data.name").value(name))
                .andExpect(jsonPath("$.data.releaseDate").value(date.toString()));
    }

    @Test
    @WithMockUser()
    void shouldGetNullIfFilmsDontExist() throws Exception {
        mockMvc.perform(get("/films"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldThrowForbiddenExceptionWhenCreateFilmWithUserRole() throws Exception {
        String name = "Test Name";
        Date date = new Date(1);

        FilmDto filmDto = new FilmDto(null, name, date, null, null, null);

        mockMvc.perform(post("/films")
                        .content(asJsonString(filmDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.statusCode").value(403))
                .andExpect(jsonPath("$.message").value("Access Denied"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldThrowValidationExceptionWhenFilmNameIsNull() throws Exception {
        Date date = new Date(1);

        FilmDto filmDto = new FilmDto(null, null, date, null, null, null);

        mockMvc
                .perform(post("/films")
                        .content(asJsonString(filmDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("{name=must not be blank}"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldThrowValidationExceptionWhenFilmNameIsEmpty() throws Exception {
        Date date = new Date(1);

        FilmDto filmDto = new FilmDto(null, "", date, null, null, null);

        mockMvc
                .perform(post("/films")
                        .content(asJsonString(filmDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("{name=must not be blank}"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldThrowValidationExceptionWhenReleaseDateIsNull() throws Exception {
        String name = "Test Name";

        FilmDto filmDto = new FilmDto(null, name, null, null, null, null);

        mockMvc
                .perform(post("/films")
                        .content(asJsonString(filmDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("{releaseDate=must not be null}"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldCreateFilm() throws Exception {
        String name = "Test Name";
        Date date = new Date(1);

        FilmDto filmDto = new FilmDto(null, name, date, null, null, null);

        MvcResult mvcResult = mockMvc
                .perform(post("/films")
                        .content(asJsonString(filmDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.name").value(name))
                .andExpect(jsonPath("$.data.releaseDate").value(date.toString()))
                .andReturn();

        //на сколько норм так делать?
        String contentAsString = mvcResult.getResponse().getContentAsString();
        TypeReference<APIResponse<FilmDto>> typeRef = new TypeReference<>() {};
        APIResponse<FilmDto> response = objectMapper.readValue(contentAsString, typeRef);
        Optional<Film> savedFilm = filmRepository.findById(response.getData().id());

        assertTrue(savedFilm.isPresent());
        assertNotNull(savedFilm.get());
        assertEquals(name, savedFilm.get().getName());
        assertEquals(date.toString(), savedFilm.get().getReleaseDate().toString());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldThrowForbiddenExceptionWhenUpdateFilmWithUserRole() throws Exception {
        String name = "Test Name";
        Date date = new Date(1);

        FilmDto filmDto = new FilmDto(null, name, date, null, null, null);

        mockMvc.perform(put("/films/{id}", 123)
                        .content(asJsonString(filmDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.statusCode").value(403))
                .andExpect(jsonPath("$.message").value("Access Denied"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldUpdateFilm() throws Exception {
        String oldName = "Test Updated Name";
        Date oldDate = new Date(999999999999L);
        String newName = "New Name";
        Date newDate = new Date(1L);

        FilmDto filmDto = new FilmDto(null, oldName, oldDate, null, null, null);
        Film savedFilm = filmRepository.save(new Film(null, newName, newDate, null, null, null, null));

        MvcResult mvcResult = mockMvc
                .perform(put("/films/{id}", savedFilm.getId())
                        .content(asJsonString(filmDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.name").value(oldName))
                .andExpect(jsonPath("$.data.releaseDate").value(oldDate.toString()))
                .andReturn();

        //на сколько норм так делать?
        Optional<Film> updatedFilm = filmRepository.findById(savedFilm.getId());

        assertTrue(updatedFilm.isPresent());
        assertNotNull(updatedFilm.get());
        assertEquals(oldName, updatedFilm.get().getName());
        assertEquals(oldDate.toString(), updatedFilm.get().getReleaseDate().toString());
    }


    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldThrowForbiddenExceptionWhenDeleteFilmWithUserRole() throws Exception {
        mockMvc.perform(delete("/films/{id}", 123))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.statusCode").value(403))
                .andExpect(jsonPath("$.message").value("Access Denied"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldDeleteFilm() throws Exception {
        Film savedFilm = filmRepository.save(new Film(null, "new Name", new Date(1L), null, null, null, null));

        assertEquals(1, filmRepository.findAll().size());

        mockMvc.perform(delete("/films/{id}", savedFilm.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Film with ID " + savedFilm.getId() + " was removed"));

        assertEquals(0, filmRepository.findAll().size());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}