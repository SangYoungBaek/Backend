package com.starta.project.domain.quiz.entity;

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
    @JoinColumn(name = "quiz_question_id",nullable = false)
    private QuizQuestion quizQuestion;

    public void set(CreateQuizChoicesDto createQuizChoicesDto, QuizQuestion quizQuestion) {
        this.answer = createQuizChoicesDto.getAnswer();
        this.checks = createQuizChoicesDto.isChecks();
        this.quizQuestion = quizQuestion;
    }
    // getters, setters, etc.
}
