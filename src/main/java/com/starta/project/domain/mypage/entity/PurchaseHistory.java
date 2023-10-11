package com.starta.project.domain.mypage.entity;

import com.starta.project.domain.member.entity.MemberDetail;
import com.starta.project.domain.mileageshop.entity.MileageShopItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mileageItem_id",nullable = false)
    private MileageShopItem mileageShopItem;

    @ManyToOne
    @JoinColumn(name = "memberDetail_id",nullable = false)
    private MemberDetail memberDetail;

    @Column(nullable = false)
    private LocalDateTime orderedAt = LocalDateTime.now();

    @Column(nullable = false)
    private Integer quantity;

    public PurchaseHistory(MileageShopItem mileageShopItem, MemberDetail memberDetail, Integer quantity) {
        this.quantity = quantity;
        this.mileageShopItem = mileageShopItem;
        this.memberDetail = memberDetail;
    }
}

