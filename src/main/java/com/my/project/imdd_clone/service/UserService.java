package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.UserDto;
import com.my.project.imdd_clone.mapper.UserMapper;
import com.my.project.imdd_clone.model.User;
import com.my.project.imdd_clone.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public User getByUsername(String username) {
       return repository.findByUsername(username)
            .orElseThrow(() ->  new NoSuchElementException("user not found!"));
    }

    public UserDto getDtoByUsername(String username) {
        return mapper.toDto(getByUsername(username));
    }

    public User save(User user) {
        return repository.save(user);
    }
}
