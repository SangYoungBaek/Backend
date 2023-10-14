package com.starta.project.domain.quiz.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.starta.project.domain.quiz.dto.UpdateQuizQuestionDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer questionNum = 0;

    @Column(nullable = false)
    private String quizTitle;

    @Column
    private String image;

    @Column(nullable = false)
    private String quizContent;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    public void set(Quiz quiz, Integer questionNum, String title, String content, String dtoImage) {
        this.quiz = quiz;
        this.quizTitle = title;
        this.quizContent = content;
        this.image = dtoImage;
        this.questionNum = questionNum;
    }

    public void update(UpdateQuizQuestionDto updateQuizQuestionDto) {
        this.quizTitle = updateQuizQuestionDto.getTitle();
        this.image = updateQuizQuestionDto.getImage();
        this.quizContent = updateQuizQuestionDto.getContent();
    }
}
