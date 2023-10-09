package com.starta.project.domain.quiz.dto;

import com.starta.project.domain.quiz.entity.Quiz;
import com.starta.project.domain.quiz.entity.QuizChoices;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateQuestiontRequestDto {

    private String title;
    private String content;
    private String image;
    private List<CreateQuizChoicesDto> quizChoices;

}