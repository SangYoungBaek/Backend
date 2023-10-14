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

    @Column(nullable = false)
    private Integer mileagePoint;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private Integer complaint;

    @Column
    private String image;

    @Column(nullable = false)
    private Integer totalScore;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberDetail(String nickname) {
        this.nickname = nickname;
        this.mileagePoint = 0;
        this.complaint = 0;
        this.totalScore = 0;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    public void changeMileagePoint(Integer totalPrice) {
        this.mileagePoint -= totalPrice;
    }
}

