package com.starta.project.domain.quiz.controller;

import com.starta.project.domain.quiz.dto.CreateQuestiontRequestDto;
import com.starta.project.domain.quiz.service.QuizQuestionService;
import com.starta.project.global.messageDto.MsgResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class QuziQuestionController {

    private final QuizQuestionService quizQuestionService;

    @PostMapping("/quiz/{id}/quizQuestion")
    public ResponseEntity<MsgResponse> craeteQuizQuestion (@PathVariable Long id,
                                                           @RequestBody CreateQuestiontRequestDto createQuestiontRequestDto) {
       return quizQuestionService.createQuizQuestion(id, createQuestiontRequestDto) ;
    }
}
