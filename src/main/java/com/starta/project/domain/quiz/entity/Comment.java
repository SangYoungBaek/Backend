package com.starta.project.domain.quiz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starta.project.domain.member.entity.Member;
import com.starta.project.domain.quiz.dto.CreateCommentRequestDto;
import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column
    private Integer complainInt = 0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "quiz_id",nullable = false)
    private Quiz quiz;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void set(Quiz quiz, CreateCommentRequestDto createCommentRequestDto, Member member) {
        this.comment = createCommentRequestDto.getContent();
        this.quiz = quiz;
        this.member = member;
    }

    public void update(String content) {
        this.comment = content;
    }
}

