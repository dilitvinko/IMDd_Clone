package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.LoginRequest;
import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.DTO.TokenDto;
import com.my.project.imdd_clone.clients.AuthClient;
import com.my.project.imdd_clone.clients.RatingClient;
import com.my.project.imdd_clone.controller.response.APIResponse;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.repository.FilmRepository;
import com.my.project.imdd_clone.repository.RatingRepository;
import feign.FeignException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableFeignClients(basePackages = "com.my.project.imdd_clone.clients")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingApiTest {

    @Autowired
    private RatingClient ratingClient;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private FilmRepository filmRepository;

    private String userToken;
    private Long userId;
    private String adminToken;
    private Long adminId;
    private Long createdFilmId;

    @BeforeAll
    public void auth() {
        APIResponse<TokenDto> userTokenDto = authClient.login(new LoginRequest("dilit", "password"));
        APIResponse<TokenDto> adminTokenDto = authClient.login(new LoginRequest("adminlogin", "password"));
        userToken = "Bearer " + userTokenDto.getData().accessToken();
        adminToken = "Bearer " + adminTokenDto.getData().accessToken();
        userId = userTokenDto.getData().user().id();
        adminId = userTokenDto.getData().user().id();

        createdFilmId = filmRepository.save(new Film(null, "Test name", null, null, null, null, null)).getId();
    }

    @AfterAll
    public void removeAllRatingsFromDb() {
        ratingRepository.deleteAll();
    }


    @Test
    public void testCreateRating_Success() throws Exception {
        //Arrange
        RatingDto ratingDto = new RatingDto(null, 8, userId, createdFilmId);
        RatingDto expected = new RatingDto(null, 8, userId, createdFilmId);

        //Act
        APIResponse<RatingDto> resDto = ratingClient.update(ratingDto, userToken);

        //Assert
        assertTrue(new ReflectionEquals(expected, "id").matches(resDto.getData()));
    }

    @Test
    public void testCreateRating_InvalidPoints_LessThanMinimum() {
        RatingDto invalidRatingDto = new RatingDto(null, 0, userId, createdFilmId);
        assertThrows(FeignException.BadRequest.class, () -> ratingClient.update(invalidRatingDto, userToken));
    }

    @Test
    public void testCreateRating_InvalidPoints_GreaterThanMaximum() {
        RatingDto invalidRatingDto = new RatingDto(null, 11, userId, createdFilmId);
        assertThrows(FeignException.BadRequest.class, () -> ratingClient.update(invalidRatingDto, userToken));
    }

    @Test
    public void testCreateRating_MissingFilmId() {
        RatingDto invalidRatingDto = new RatingDto(null, 8, userId, null);
        assertThrows(FeignException.BadRequest.class, () -> ratingClient.update(invalidRatingDto, userToken));
    }

    @Test
    public void testCreateRating_Unauthorized() {
        RatingDto ratingDto = new RatingDto(null, 8, userId, createdFilmId);
        assertThrows(FeignException.Unauthorized.class, () -> ratingClient.update(ratingDto, ""));
    }
}