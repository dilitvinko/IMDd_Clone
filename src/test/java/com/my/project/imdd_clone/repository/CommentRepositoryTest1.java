package com.my.project.imdd_clone.repository;

import com.my.project.imdd_clone.model.Film;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.my.project.imdd_clone.model.Comment;
import com.my.project.imdd_clone.model.User;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest1 {

    @Autowired
    private CommentRepository commentRepository;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.6"));

    @DynamicPropertySource
    static void mySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.driverClassName", () -> postgreSQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
        registry.add("spring.liquibase.change-log", () -> "classpath:db/changelog/db.changelog-master.yaml");
    }

    @BeforeEach
    public void cleanUp() {
        commentRepository.deleteAll();
    }

    @Test
    void shouldSave() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setName("Test name");
        user.setPassword("pass");
        user.setUsername("username1");

        Film film = new Film();
        film.setName("test film");


        Comment comment = new Comment();
        comment.setText("test_tesxt");
        comment.setUser(user);
        comment.setFilm(film);
        commentRepository.save(comment);


        var result = commentRepository.findAll();
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void shouldFindByFilmId() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setName("Test name");
        user.setPassword("pass");
        user.setUsername("username1");

        User user2 = new User();
        user2.setEmail("test2@test2.com");
        user2.setName("Test name2");
        user2.setPassword("pass");
        user2.setUsername("username2");

        Film film = new Film();
        film.setName("test film");


        Comment comment = new Comment();
        comment.setText("test_tesxt");
        comment.setUser(user);
        comment.setFilm(film);
        Comment save = commentRepository.save(comment);

        Comment comment2 = new Comment();
        comment2.setText("test_tesxt");
        comment2.setUser(user2);
        comment2.setFilm(film);
        commentRepository.save(comment2);


        var result = commentRepository.getAllByFilm_Id(save.getFilm().getId(), PageRequest.of(0, 10));
        Assertions.assertEquals(2, result.getContent().size());
    }


}