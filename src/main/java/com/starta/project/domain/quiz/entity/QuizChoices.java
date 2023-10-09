package com.starta.project.domain.quiz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starta.project.domain.quiz.dto.CreateQuizChoicesDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class QuizChoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private boolean checks;

    @ManyToOne
    @JsonIgnore // 조회를 하려는 중에 무한 재귀 현상 발생 -> 그로 인하여 Ignore을 활용하여 조회를 방지하는 해결
    @JoinColumn(name = "quiz_question_id",nullable = false)
    private QuizQuestion quizQuestion;

    public void set(CreateQuizChoicesDto createQuizChoicesDto, QuizQuestion quizQuestion) {
        this.answer = createQuizChoicesDto.getAnswer();
        this.checks = createQuizChoicesDto.isChecks();
        this.quizQuestion = quizQuestion;
    }
    // getters, setters, etc.
}
