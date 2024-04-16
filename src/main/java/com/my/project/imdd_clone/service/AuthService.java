package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.LoginRequest;
import com.my.project.imdd_clone.DTO.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public TokenDto login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));
            return tokenService.generateTokenPairs(loginRequest.getUsername());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
