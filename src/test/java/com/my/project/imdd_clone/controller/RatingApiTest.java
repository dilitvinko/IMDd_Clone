package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.LoginRequest;
import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.DTO.TokenDto;
import com.my.project.imdd_clone.clients.AuthClient;
import com.my.project.imdd_clone.clients.RatingClient;
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
import org.springframework.web.client.HttpClientErrorException;

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

    private String userToken;
    private Long userId;
    private String adminToken;
    private Long adminId;

    @BeforeAll
    public void auth() {
        TokenDto userTokenDto = authClient.login(new LoginRequest("dilit", "password"));
        TokenDto adminTokenDto = authClient.login(new LoginRequest("adminlogin", "password"));
        userToken = "Bearer " + userTokenDto.accessToken();
        adminToken = "Bearer " + adminTokenDto.accessToken();
        userId = userTokenDto.user().id();
        adminId = userTokenDto.user().id();
    }

    @AfterAll
    public void removeAllRatingsFromDb() {
        ratingRepository.deleteAll();
    }


    @Test
    public void testCreateRating_Success() throws Exception {
        //Arrange
        RatingDto ratingDto = new RatingDto(null, 8, userId, 1L);
        RatingDto expected = new RatingDto(null, 8, userId, 1L);

        //Act
        RatingDto resDto = ratingClient.update(ratingDto, userToken);

        //Assert
        assertTrue(new ReflectionEquals(expected, "id").matches(resDto));
    }

    @Test
    public void testCreateRating_InvalidPoints_LessThanMinimum() {
        RatingDto invalidRatingDto = new RatingDto(null, 0, userId, 1L);
        assertThrows(FeignException.BadRequest.class, () -> ratingClient.update(invalidRatingDto, userToken));
    }

    @Test
    public void testCreateRating_InvalidPoints_GreaterThanMaximum() {
        RatingDto invalidRatingDto = new RatingDto(null, 11, userId, 1L);
        assertThrows(FeignException.BadRequest.class, () -> ratingClient.update(invalidRatingDto, userToken));
    }

    @Test
    public void testCreateRating_MissingFilmId() {
        RatingDto invalidRatingDto = new RatingDto(null, 8, userId, null);
        assertThrows(FeignException.BadRequest.class, () -> ratingClient.update(invalidRatingDto, userToken));
    }

    @Test
    public void testCreateRating_Unauthorized() {
        RatingDto ratingDto = new RatingDto(null, 8, userId, 1L);
        assertThrows(FeignException.Unauthorized.class, () -> ratingClient.update(ratingDto, ""));
    }

    @Test
    public void testCreateRating_AdminRole() {
        RatingDto ratingDto = new RatingDto(null, 8, userId, 1L);
        assertThrows(FeignException.Forbidden.class, () -> ratingClient.update(ratingDto, adminToken));
    }
}



//    @Test
//    @WithMockUser(roles = "USER")
//    public void testCreateRating_InvalidData() throws Exception {
//        RatingDto invalidRatingDto = new RatingDto(null, 15, 1L, 1L);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/ratings")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidRatingDto)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void testCreateRating_Success_AdminRole() throws Exception {
//        RatingDto ratingDto = new RatingDto(null, 8, 1L, 1L);
//        when(ratingService.create(any(RatingDto.class), any(User.class))).thenReturn(ratingDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/ratings")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(ratingDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//    }

//    @Test
//    public void testCreateRating_Unauthorized() throws Exception {
//        RatingDto ratingDto = new RatingDto(null, 8, 1L, 1L);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/ratings")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(ratingDto)))
//                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
//    }

//    @Test
//    public void testCreateRating_MissingRequiredFields() throws Exception {
//        RatingDto ratingDto = new RatingDto(null, 8, null, 1L);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/ratings")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(ratingDto)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }

//    @Test
//    public void testCreateRating_ServiceError() throws Exception {
//        RatingDto ratingDto = new RatingDto(null, 8, 1L, 1L);
//        when(ratingService.create(any(RatingDto.class), any(User.class))).thenThrow(new RuntimeException("Service error"));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/ratings")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(ratingDto)))
//                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
//    }