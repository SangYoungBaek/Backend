package com.starta.project.domain.quiz.dto;

import lombok.Getter;

@Getter
public class CreateQuizRequestDto {
    private String title;
    private String category;
    private String content;
    private String image;
}
