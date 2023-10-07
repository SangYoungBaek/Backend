package com.starta.project.domain.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer mileagePoint;

    @Column(nullable = false)
    private String nickname;

    private Integer complaint;

    @Column
    private String image;   // 마이페이지에서 바꾸기로

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 회원가입 최초생성
    public MemberDetail(String nickname) {
        this.nickname = nickname;
        this.mileagePoint = 0;
        this.complaint = 0;
    }
    public void setMember(Member member) {
        this.member = member;
    }
}

