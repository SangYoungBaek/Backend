package com.starta.project.domain.member.controller;

import com.starta.project.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberViewController {

    private MemberService memberService;

    @GetMapping("/member/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/member/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/test")
    @ResponseBody
    public void test(){
        System.out.println("TEST 성공");
    }

}
