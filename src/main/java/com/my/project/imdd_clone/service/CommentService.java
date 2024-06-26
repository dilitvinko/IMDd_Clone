package com.my.project.imdd_clone.service;

import com.my.project.imdd_clone.DTO.CommentDto;
import com.my.project.imdd_clone.mapper.CommentMapper;
import com.my.project.imdd_clone.model.Comment;
import com.my.project.imdd_clone.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentDto> getAllFilmComments(Long filmId, Pageable pageable) {
        Page<Comment> allByFilm_id = commentRepository.getAllByFilm_Id(filmId, pageable);
        return allByFilm_id.getContent().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public CommentDto create(CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }
}
