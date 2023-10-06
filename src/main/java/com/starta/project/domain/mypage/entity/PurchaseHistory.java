package com.starta.project.domain.mypage.entity;

import com.starta.project.domain.member.entity.MemberDetail;
import com.starta.project.domain.mileageshop.entity.MileageItem;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mileageItem_id",nullable = false)
    private MileageItem mileageItem;

    @ManyToOne
    @JoinColumn(name = "memberDetail_id",nullable = false)
    private MemberDetail memberDetail;
    // getters, setters, etc.
}

