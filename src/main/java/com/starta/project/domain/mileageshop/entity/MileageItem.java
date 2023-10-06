package com.starta.project.domain.mileageshop.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MileageItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String content;

    private String image;
    // getters, setters, etc.
}

