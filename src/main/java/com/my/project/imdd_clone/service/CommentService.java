package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.CommentDto;
import com.my.project.imdd_clone.mapper.CommentMapper;
import com.my.project.imdd_clone.model.Comment;
import com.my.project.imdd_clone.model.User;
import com.my.project.imdd_clone.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentDto> getAllFilmComments(Long filmId) {
        List<Comment> allByFilm_id = commentRepository.getAllByFilm_Id(filmId);
        return allByFilm_id.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public CommentDto create(CommentDto commentDto, User user) {
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setUser(user);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }
}
