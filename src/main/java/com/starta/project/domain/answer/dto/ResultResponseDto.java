package com.starta.project.domain.answer.dto;

import com.starta.project.domain.quiz.entity.Comment;
import com.starta.project.domain.quiz.entity.Quiz;
import lombok.Getter;

import java.util.List;

@Getter
public class ResultResponseDto {
    private Long id;
    private String title;
    private String content;
    private String image;
    private Integer likeNum;
    private Integer viewNum;
    private List<Comment> comments;

    public void set(Quiz quiz, List<Comment> list) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.content = quiz.getContent();
        this.image = quiz.getImage();
        this.viewNum = quiz.getViewCount();
        this.likeNum = quiz.getLikes();
        this.comments = list;
    }
}
