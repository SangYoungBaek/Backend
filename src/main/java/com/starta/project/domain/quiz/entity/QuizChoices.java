package com.starta.project.domain.quiz.entity;

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
    private boolean check;

    @ManyToOne
    @JoinColumn(name = "quizQuestion_id",nullable = false)
    private QuizQuestion quizQuestion;
    // getters, setters, etc.
}
