package com.starta.project.domain.quiz.controller;

import com.starta.project.domain.quiz.dto.CreateQuizRequestDto;
import com.starta.project.domain.quiz.dto.ShowQuizResponseDto;
import com.starta.project.domain.quiz.dto.SimpleQuizDto;
import com.starta.project.domain.quiz.service.QuizService;
import com.starta.project.global.messageDto.MsgDataResponse;
import com.starta.project.global.messageDto.MsgResponse;
import com.starta.project.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/quiz")
    public ResponseEntity<MsgDataResponse> createQuiz (@RequestBody CreateQuizRequestDto quizRequestDto,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return quizService.createQuiz(quizRequestDto, userDetails.getMember() );
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<ShowQuizResponseDto> showQuiz (@PathVariable Long id) {
        return quizService.showQuiz(id);
    }

    @DeleteMapping("/quiz/{id}")
    public ResponseEntity<MsgResponse> deleteQuiz(@PathVariable Long id,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(quizService.deleteQuiz(id,userDetails.getMember()));
    }

    @PostMapping("/quiz/{id}/quizLikes")
    public ResponseEntity<MsgResponse> pushLikes (@PathVariable Long id,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(quizService.pushLikes(id, userDetails.getMember()));
    }


    // 수정이라 주석처리
//    @PutMapping("/quiz/{id}")
//    public ResponseEntity<MsgResponse> updateQuiz (@PathVariable Long id,
//                                                   @RequestBody CreateQuizRequestDto quizRequestDto) {
//        return ResponseEntity.ok(quizService.update(id,quizRequestDto));
//    }
}
