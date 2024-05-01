package com.my.project.imdd_clone.clients;

import com.my.project.imdd_clone.DTO.RatingDto;
import com.my.project.imdd_clone.controller.response.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ratings", url = "${test.url}")
public interface RatingClient {

    @PostMapping(value = "/ratings")
    APIResponse<RatingDto> update(@RequestBody RatingDto ratingDto, @RequestHeader("Authorization") String token);
}
