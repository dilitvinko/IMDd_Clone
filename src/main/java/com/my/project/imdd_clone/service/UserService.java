package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.UserDto;
import com.my.project.imdd_clone.mapper.UserMapper;
import com.my.project.imdd_clone.model.User;
import com.my.project.imdd_clone.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public User getByUsername(String username) {
        var user = repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("user not found!");
        }
        return user.get();
    }

    public UserDto getDtoByUsername(String username) {
        return mapper.toDto(getByUsername(username));
    }
}
