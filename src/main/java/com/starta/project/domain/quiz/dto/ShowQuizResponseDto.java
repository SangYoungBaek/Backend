package com.starta.project.domain.quiz.dto;

import com.starta.project.domain.quiz.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

public class ShowQuizResponseDto {
    private Long id;
    private String title;
    private String username;
    private String image;
    private LocalDateTime createdTime;
    private String category;
    private List<Comment> comments;
}
