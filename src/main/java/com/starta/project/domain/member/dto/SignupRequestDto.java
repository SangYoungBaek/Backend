package com.starta.project.domain.member.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;


@Getter
public class SignupRequestDto {

    @Pattern(
            regexp = "^[a-z][a-z0-9]{4,20}+$",
            message = "username은  최소 4자 이상, 15자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 한다."
    )
    private String username;

    @Pattern(
            regexp = "^[a-zA-Z가-힣0-9\\s]{4,20}$",
            message = "nickname은  최소 4자 이상, 20자 이하로 구성되어야 한다.(알파벳, 한글, 숫자, 띄어쓰기 허용)"
    )
    private String nickname;

    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "password는  최소 8자 이상, 20자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 한다."
    )
    private String password;

    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "password는  최소 8자 이상, 20자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 한다."
    )
    private String checkpassword;   // 패스워드 확인

    private boolean admin = false; // 기본값은 "USER"로 설정

}

