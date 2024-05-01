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
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final UserService userService;
    private final UserMapper userMapper;

    public TokenDto login(LoginRequest loginRequest) {
        return tokenService.generateTokenPairs(loginRequest.username());
    }

    public TokenDto registerNewUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setRoles(Set.of(new Role("USER")));
        User userSaved = userService.save(user);
        return tokenService.generateTokenPairs(userSaved.getUsername());
    }
}
