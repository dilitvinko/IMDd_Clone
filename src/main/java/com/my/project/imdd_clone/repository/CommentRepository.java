package com.my.project.imdd_clone.repository;

import com.my.project.imdd_clone.model.Comment;
import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> getAllByFilm_Id(Long filmId, Pageable pageable);
}
