package com.starta.project.domain.quiz.service;

import com.starta.project.domain.quiz.dto.CreateQuestiontRequestDto;
import com.starta.project.domain.quiz.dto.CreateQuizChoicesDto;
import com.starta.project.domain.quiz.entity.Quiz;
import com.starta.project.domain.quiz.entity.QuizChoices;
import com.starta.project.domain.quiz.entity.QuizQuestion;
import com.starta.project.domain.quiz.repository.QuizChoicesRepository;
import com.starta.project.domain.quiz.repository.QuizQuestionRepository;
import com.starta.project.domain.quiz.repository.QuizRepository;
import com.starta.project.global.messageDto.MsgResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizQuestionService {

    private final QuizRepository quizRepository;
    private final QuizChoicesRepository quizChoicesRepository;
    private final QuizQuestionRepository quizQuestionRepository;

    public ResponseEntity<MsgResponse> createQuizQuestion(Long id, CreateQuestiontRequestDto createQuestiontRequestDto) {
        //문제 찾기
        Quiz quiz = quizRepository.findById(id).orElseThrow( ()
                -> new NullPointerException("해당 퀴즈가 없습니다. "));
        //문제 번호
        Integer questionNum = 0;
        //findTop == 가장 먼저 찾을 수 있는 항목
        Optional<QuizQuestion> question =  quizQuestionRepository.findTopByQuizOrderByQuestionNumDesc(quiz);
        if (question.isPresent()){
            questionNum = question.get().getQuestionNum();
        }

        QuizQuestion quizQuestion = new QuizQuestion();
        questionNum++;
        quizQuestion.set(quiz,questionNum ,createQuestiontRequestDto.getTitle(), createQuestiontRequestDto.getContent(),
                createQuestiontRequestDto.getImage());
        quizQuestionRepository.save(quizQuestion);

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
