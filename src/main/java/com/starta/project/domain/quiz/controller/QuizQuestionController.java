package com.starta.project.domain.quiz.controller;

import com.starta.project.domain.quiz.dto.CreateQuestionRequestDto;
import com.starta.project.domain.quiz.dto.ShowQuestionResponseDto;
import com.starta.project.domain.quiz.entity.QuizQuestion;
import com.starta.project.domain.quiz.service.QuizQuestionService;
import com.starta.project.global.messageDto.MsgResponse;
import com.starta.project.global.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuizQuestionController {

    private final QuizQuestionService quizQuestionService;

    @PostMapping("/quiz/{id}/quizQuestion")
    public ResponseEntity<MsgResponse> createQuizQuestion (@PathVariable Long id,
                                                           @RequestPart("requestDto") CreateQuestionRequestDto createQuestionRequestDto,
                                                           @RequestPart("image") MultipartFile multipartFile,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
       return quizQuestionService.createQuizQuestion(id,multipartFile ,createQuestionRequestDto, userDetails.getMember()) ;
    }

    @GetMapping("/quiz/{id}/quizQuestion/{questionNum}")
    public ResponseEntity<ShowQuestionResponseDto> showQuizQuestion (@PathVariable Long id,
                                                                     @PathVariable Integer questionNum){
        return ResponseEntity.ok(quizQuestionService.showQuizQuestion(id, questionNum));
    }

    @DeleteMapping("/quiz/{id}/quizQuestion/{questionNum}")
    public ResponseEntity<MsgResponse> deleteQuizQuestion (@PathVariable Long id,
                                                           @PathVariable Integer questionNum,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return quizQuestionService.deleteQuizQuestion(id,questionNum, userDetails.getMember());
    }
    @DeleteMapping("/quiz/quizQuestion/quizChoices/{id}")
    public ResponseEntity<MsgResponse> deleteQuizChoices (@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        return quizQuestionService.deleteChoices(id, userDetails.getMember());
    }


    //수정이라 주석 처리

//    @PutMapping("/quizQuestion/{id}")
//    private ResponseEntity<MsgResponse> updateQuizQuestion (@PathVariable Long id,
//                                                            @RequestBody UpdateQuizQuestionDto updateQuizQuestionDto){
//        return ResponseEntity.ok(quizQuestionService.updateQuizQuestion(id, updateQuizQuestionDto));
//    }

}
