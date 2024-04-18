package com.my.project.imdd_clone.repository;

import com.my.project.imdd_clone.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FilmRepository extends JpaRepository<Film, Long>, QuerydslPredicateExecutor<Film> {
}
