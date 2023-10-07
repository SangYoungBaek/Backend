package com.starta.project.domain.quiz.controller;

import com.starta.project.domain.member.entity.Member;
import com.starta.project.domain.quiz.dto.CreateQuizRequestDto;
import com.starta.project.domain.quiz.dto.ShowQuizResponseDto;
import com.starta.project.domain.quiz.service.QuizService;
import com.starta.project.global.messageDto.MsgDataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MinController {
    private QuizService quizService;

    @PostMapping("/quiz")
    public ResponseEntity<MsgDataResponse> createQuiz (@RequestBody CreateQuizRequestDto quizRequestDto , Member member) {
        return quizService.createQuiz(quizRequestDto, member);
    }

}
