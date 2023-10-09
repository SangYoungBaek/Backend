package com.starta.project.domain.quiz.controller;

import com.starta.project.domain.quiz.dto.CreateQuestionRequestDto;
import com.starta.project.domain.quiz.dto.ShowQuestionResponseDto;
import com.starta.project.domain.quiz.entity.QuizQuestion;
import com.starta.project.domain.quiz.service.QuizQuestionService;
import com.starta.project.global.messageDto.MsgResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuizQuestionController {

    private final QuizQuestionService quizQuestionService;

    @PostMapping("/quiz/{id}/quizQuestion")
    public ResponseEntity<MsgResponse> createQuizQuestion (@PathVariable Long id,
                                                           @RequestBody CreateQuestionRequestDto createQuestionRequestDto) {
       return quizQuestionService.createQuizQuestion(id, createQuestionRequestDto) ;
    }

    @GetMapping("/quiz/{id}/quizQuestion/{questionNum}")
    public ResponseEntity<ShowQuestionResponseDto> showQuizQuestion (@PathVariable Long id,
                                                                     @PathVariable Integer questionNum){
        return ResponseEntity.ok(quizQuestionService.showQuizQuestion(id, questionNum));
    }

    @DeleteMapping("/quiz/{id}/quizQuestion/{questionNum}")
    public ResponseEntity<MsgResponse> deleteQuizQuestion (@PathVariable Long id,
                                                           @PathVariable Integer questionNum) {
        return ResponseEntity.ok(quizQuestionService.deleteQuizQuestion(id,questionNum));
    }
    @DeleteMapping("/quiz/quizQuestion/quizChoices/{id}")
    public ResponseEntity<MsgResponse> deleteQuizChoices (@PathVariable Long id){
        return ResponseEntity.ok(quizQuestionService.deleteChoices(id));
    }


    //수정이라 주석 처리

//    @PutMapping("/quizQuestion/{id}")
//    private ResponseEntity<MsgResponse> updateQuizQuestion (@PathVariable Long id,
//                                                            @RequestBody UpdateQuizQuestionDto updateQuizQuestionDto){
//        return ResponseEntity.ok(quizQuestionService.updateQuizQuestion(id, updateQuizQuestionDto));
//    }

}
