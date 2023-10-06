package com.starta.project.domain.mileageshop.entity;

import com.starta.project.domain.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalScore;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    // getters, setters, etc.
}

