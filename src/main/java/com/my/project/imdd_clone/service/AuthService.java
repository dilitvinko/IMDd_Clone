package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.LoginRequest;
import com.my.project.imdd_clone.DTO.TokenDto;
import com.my.project.imdd_clone.DTO.UserDto;
import com.my.project.imdd_clone.mapper.UserMapper;
import com.my.project.imdd_clone.model.Role;
import com.my.project.imdd_clone.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;

    public TokenDto login(LoginRequest loginRequest) {
        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getUsername(), loginRequest.getPassword()));
            return tokenService.generateTokenPairs(loginRequest.username());
        } catch (Exception e) {
            throw e;
        }
    }

    public TokenDto registerNewUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setRoles(Set.of(new Role("USER")));
        User userSaved = userService.save(user);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userSaved.getUsername(), userSaved.getPassword()));
            return tokenService.generateTokenPairs(userSaved.getUsername());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
