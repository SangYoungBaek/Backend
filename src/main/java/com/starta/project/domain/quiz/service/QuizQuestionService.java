package com.starta.project.domain.quiz.service;

import com.starta.project.domain.quiz.dto.CreateQuestiontRequestDto;
import com.starta.project.domain.quiz.dto.CreateQuizChoicesDto;
import com.starta.project.domain.quiz.entity.Quiz;
import com.starta.project.domain.quiz.entity.QuizChoices;
import com.starta.project.domain.quiz.entity.QuizQuestion;
import com.starta.project.domain.quiz.repository.QuizChoicesRepository;
import com.starta.project.domain.quiz.repository.QuizQuestionRepositoty;
import com.starta.project.domain.quiz.repository.QuizRepository;
import com.starta.project.global.messageDto.MsgResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizQuestionService {

    private final QuizRepository quizRepository;
    private final QuizChoicesRepository quizChoicesRepository;
    private final QuizQuestionRepositoty quizQuestionRepositoty;

    public ResponseEntity<MsgResponse> createQuizQuestion(Long id, CreateQuestiontRequestDto createQuestiontRequestDto) {

        Quiz quiz = quizRepository.findById(id).orElseThrow( ()
                -> new NullPointerException("해당 퀴즈가 없습니다. "));

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.set(quiz, createQuestiontRequestDto.getTitle(), createQuestiontRequestDto.getContent(),
                createQuestiontRequestDto.getImage());
        quizQuestionRepositoty.save(quizQuestion);

        List<CreateQuizChoicesDto> quizChoicesList = createQuestiontRequestDto.getQuizChoices();
        List<QuizChoices> quizChoices = new ArrayList<>();
        for (CreateQuizChoicesDto createQuizChoicesDto : quizChoicesList) {
            QuizChoices quizChoices1 = new QuizChoices();
            quizChoices1.set(createQuizChoicesDto,quizQuestion);
            quizChoices.add(quizChoices1);
        }
        quizChoicesRepository.saveAll(quizChoices);
        MsgResponse msg = new MsgResponse("문제 생성을 성공 하셨습니다!");
        return ResponseEntity.status(200).body(msg);
    }
}
