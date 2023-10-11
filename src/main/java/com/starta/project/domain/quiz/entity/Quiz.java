package com.starta.project.domain.quiz.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.starta.project.domain.member.entity.Member;
import com.starta.project.domain.quiz.dto.CreateQuizRequestDto;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer viewCount = 0;

    @Column(nullable = false)
    private Integer complainInt= 0;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private Integer likes = 0;

    @Column
    private String image;

    @Column(nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void set(CreateQuizRequestDto quizRequestDto, LocalDateTime now, Member member) {
        this.title = quizRequestDto.getTitle();
        this.category = quizRequestDto.getCategory();
        this.image = quizRequestDto.getImage();
        this.member = member;
        this.createdAt = now;
        this.content = quizRequestDto.getContent();
    }

    public void view(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public void pushLikes(Integer likesNum) {
        this.likes = likesNum;
    }

//    public void update(CreateQuizRequestDto quizRequestDto) {
//        this.title = quizRequestDto.getTitle();
//        this.content = quizRequestDto.getContent();
//        this.category = quizRequestDto.getCategory();
//        this.image = quizRequestDto.getImage();
//    }
}

