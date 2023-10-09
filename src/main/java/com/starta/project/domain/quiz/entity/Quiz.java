package com.starta.project.domain.quiz.entity;

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
    private LocalDateTime created_at;

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
        this.created_at = now;
        this.content = quizRequestDto.getContent();
    }

    public void view(Integer viewcount) {
        this.viewCount = viewcount;
    }

    public void update(CreateQuizRequestDto quizRequestDto) {
        this.title = quizRequestDto.getTitle();
        this.content = quizRequestDto.getContent();
        this.category = quizRequestDto.getCategory();
        this.image = quizRequestDto.getImage();
    }
}

