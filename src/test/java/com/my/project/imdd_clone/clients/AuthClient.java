package com.my.project.imdd_clone.clients;

import com.my.project.imdd_clone.DTO.LoginRequest;
import com.my.project.imdd_clone.DTO.TokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth", url = "${test.url}")
public interface AuthClient {

    @PostMapping(value = "/auth/login")
    TokenDto login(@RequestBody LoginRequest loginRequest);
}
