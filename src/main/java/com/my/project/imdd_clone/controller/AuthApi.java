package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.DTO.LoginRequest;
import com.my.project.imdd_clone.DTO.TokenDto;
import com.my.project.imdd_clone.DTO.UserDto;
import com.my.project.imdd_clone.controller.response.APIResponse;
import com.my.project.imdd_clone.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse<TokenDto> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenDto login = authService.login(loginRequest);
        return APIResponse.<TokenDto>builder()
                .data(login)
                .build();
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse<TokenDto> registration(@Valid @RequestBody UserDto userDto) {
        TokenDto tokenDto = authService.registerNewUser(userDto);
        return APIResponse.<TokenDto>builder()
                .data(tokenDto)
                .build();
    }

}
