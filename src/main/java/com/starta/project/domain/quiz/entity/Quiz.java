package com.starta.project.domain.quiz.entity;

import com.starta.project.domain.member.entity.Member;
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
    private Integer viewCount;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column
    private String image;

    @Column(nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    // getters, setters, etc.
}

