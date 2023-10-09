package com.starta.project.domain.quiz.controller;

import com.starta.project.domain.quiz.dto.CreateQuizRequestDto;
import com.starta.project.domain.quiz.dto.ShowQuizResponseDto;
import com.starta.project.domain.quiz.service.QuizService;
import com.starta.project.global.messageDto.MsgDataResponse;
import com.starta.project.global.messageDto.MsgResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/quiz")
    public ResponseEntity<MsgDataResponse> createQuiz (@RequestBody CreateQuizRequestDto quizRequestDto) {
        return quizService.createQuiz(quizRequestDto);
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<ShowQuizResponseDto> showQuiz (@PathVariable Long id) {
        return quizService.showQuiz(id);
    }

    @PutMapping("/quiz/{id}")
    public ResponseEntity<MsgResponse> updateQuiz (@PathVariable Long id,
                                                   @RequestBody CreateQuizRequestDto quizRequestDto) {
        return ResponseEntity.ok(quizService.update(id,quizRequestDto));
    }
}
