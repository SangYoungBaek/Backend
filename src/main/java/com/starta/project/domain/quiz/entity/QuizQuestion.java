package com.starta.project.domain.quiz.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String quizTitle;

    @Column
    private String image;

    @Column(nullable = false)
    private String quizContent;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;
    // getters, setters, etc.
}
