package com.starta.project.domain.member.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MemberDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer mileagePoint;

    @Column(nullable = false)
    private String nickname;

    private Integer complaint = 0;

    @Column(nullable = false)
    private String image;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    // getters, setters, etc.
}

