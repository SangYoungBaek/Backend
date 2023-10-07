package com.starta.project.domain.quiz.service;

import com.starta.project.domain.member.entity.Member;
import com.starta.project.domain.quiz.dto.CreateQuizRequestDto;
import com.starta.project.domain.quiz.dto.CreateQuizResponseDto;
import com.starta.project.domain.quiz.dto.ShowQuizResponseDto;
import com.starta.project.domain.quiz.entity.Comment;
import com.starta.project.domain.quiz.entity.Quiz;
import com.starta.project.domain.quiz.repository.CommentRepository;
import com.starta.project.domain.quiz.repository.QuizRepository;
import com.starta.project.global.messageDto.MsgDataResponse;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final CommentRepository commentRepository;

    public ResponseEntity<MsgDataResponse> createQuiz(CreateQuizRequestDto quizRequestDto, Member member) {
        Quiz quiz = new Quiz();
        LocalDateTime now = LocalDateTime.now();
        quiz.set(quizRequestDto, now, member);
        quizRepository.save(quiz);
        CreateQuizResponseDto quizResponseDto = new CreateQuizResponseDto();
        quizResponseDto.set(quiz.getId());
        MsgDataResponse msgDataResponse = new MsgDataResponse("퀴즈 생성 성공!" , quizRequestDto);
        return ResponseEntity.status(200).body(msgDataResponse);
    }


    private Quiz findQuiz (Long id) {
       return quizRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 퀴즈가 없습니다."));
    }

    private List<Comment> getComment(Long id) {
        List<Comment> commentList = commentRepository.findAllByQuizId(id);
        return commentList;
    }
}
