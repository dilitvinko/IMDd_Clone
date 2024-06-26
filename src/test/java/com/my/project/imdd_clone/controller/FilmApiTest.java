package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.FilmDto;
import com.my.project.imdd_clone.DTO.LoginRequest;
import com.my.project.imdd_clone.DTO.TokenDto;
import com.my.project.imdd_clone.clients.AuthClient;
import com.my.project.imdd_clone.clients.FilmClient;
import com.my.project.imdd_clone.controller.response.APIResponse;
import com.my.project.imdd_clone.repository.CommentRepository;
import com.my.project.imdd_clone.repository.FilmRepository;
import feign.FeignException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableFeignClients(basePackages = "com.my.project.imdd_clone.clients")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FilmApiTest {

    @Autowired
    private FilmClient filmClient;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CommentRepository commentRepository;

    private String userToken;
    private Long userId;
    private String adminToken;
    private Long adminId;

    @BeforeAll
    public void auth() {
        APIResponse<TokenDto> userTokenDto = authClient.login(new LoginRequest("dilit", "password"));
        APIResponse<TokenDto> adminTokenDto = authClient.login(new LoginRequest("adminlogin", "password"));
        userToken = "Bearer " + userTokenDto.getData().accessToken();
        adminToken = "Bearer " + adminTokenDto.getData().accessToken();
        userId = userTokenDto.getData().user().id();
        adminId = userTokenDto.getData().user().id();
    }

    @AfterAll
    public void cleanUp() {
        commentRepository.deleteAll();
        filmRepository.deleteAll();
    }

    @Test
    public void testCreateFilm_EmptyName() {
        //Arrange
        FilmDto invalidFilmDto = new FilmDto(null, null, Date.valueOf("2024-01-01"), "Test Description", Collections.emptyList(), 0.0);

        //Act & Assert
        assertThrows(FeignException.BadRequest.class, () -> filmClient.createFilm(invalidFilmDto, adminToken));
    }

    @Test
    public void testCreateFilm_Unauthorized() {
        //Arrange
        FilmDto filmDto = new FilmDto(null, "Test Film", Date.valueOf("2024-01-01"), "Test Description", Collections.emptyList(), 0.0);

        //Act & Assert
        assertThrows(FeignException.Unauthorized.class, () -> filmClient.createFilm(filmDto, ""));
    }

    @Test
    public void testCreateFilm_UserRole_Unauthorized() {
        //Arrange
        FilmDto filmDto = new FilmDto(null, "Test Film", Date.valueOf("2024-01-01"), "Test Description", Collections.emptyList(), 0.0);

        //Act & Assert
        assertThrows(FeignException.Forbidden.class, () -> filmClient.createFilm(filmDto, userToken));
    }

    @Test
    public void testGetFilm_UserRole_Success() {
        //Arrange
        FilmDto filmDto = new FilmDto(null, "Test Film", Date.valueOf("2024-01-01"), "Test Description", Collections.emptyList(), null);
        APIResponse<FilmDto> createdFilm = filmClient.createFilm(filmDto, adminToken);

        //Act
        APIResponse<FilmDto> retrievedFilm = filmClient.getFilm(createdFilm.getData().id(), userToken);

        //Assert
        assertTrue(new ReflectionEquals(filmDto, "id", "releaseDate").matches(retrievedFilm.getData()));
    }

    @Test
    public void testGetAllFilms_UserRole_Success() {
        //Act
        APIResponse<List<FilmDto>> filmsList = filmClient.getAllFilms(userToken);

        //Assert
        assertNotNull(filmsList);
    }

    @Test
    public void testDeleteFilm_Success_AdminRole() throws Exception {
        //Arrange
        FilmDto filmDto = new FilmDto(null, "Test Film", Date.valueOf("2024-01-01"), "Test Description", Collections.emptyList(), 0.0);
        APIResponse<FilmDto> createdFilm = filmClient.createFilm(filmDto, adminToken);

        //Act
        filmClient.deleteFilm(createdFilm.getData().id(), adminToken);

        //Assert
        assertThrows(FeignException.BadRequest.class, () -> filmClient.getFilm(createdFilm.getData().id(), adminToken));
    }

    @Test
    public void testDeleteFilm_Failure_UserRole() {
        //Arrange
        FilmDto filmDto = new FilmDto(null, "Test Film", Date.valueOf("2024-01-01"), "Test Description", Collections.emptyList(), 0.0);
        APIResponse<FilmDto> createdFilm = filmClient.createFilm(filmDto, adminToken);

        //Act & Assert
        assertThrows(FeignException.Forbidden.class, () -> filmClient.deleteFilm(createdFilm.getData().id(), userToken));
    }
}