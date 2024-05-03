package com.my.project.imdd_clone.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.my.project.imdd_clone.model.User;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest {

    @Autowired
    private UserRepository userRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.6")
            .withUsername("postgresTest")
            .withPassword("postgresTest")
            .withReuse(Boolean.TRUE);

    @BeforeEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFind() {
        var user = new User();
        user.setUsername("username1");
        user.setName("name1");
        user.setEmail("email1");
        user.setPassword("pass1");
        userRepository.save(user);

        var result = userRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindByUserName() {
        String userName = "username2";
        String email = "test123@gmail.com";
        var user = new User();
        user.setUsername(userName);
        user.setName("name2");
        user.setEmail(email);
        user.setPassword("pass2");
        userRepository.save(user);

        var result = userRepository.findByUsername(userName);
        assertTrue(result.isPresent());
        assertEquals(userName, result.get().getUsername());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    void shouldFindById() {
        var user = new User();
        user.setUsername("userName3");
        user.setName("name3");
        user.setEmail("email3");
        user.setPassword("pass3");
        User saved = userRepository.save(user);

        var result = userRepository.findById(saved.getId());
        assertTrue(result.isPresent());
        assertEquals(saved.getId(), result.get().getId());
    }
}