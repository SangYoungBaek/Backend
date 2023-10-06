package com.starta.project.domain.member.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRoleEnum role;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "memberDetail")
    private MemberDetail memberDetail;
    // getters, setters, etc.
}

