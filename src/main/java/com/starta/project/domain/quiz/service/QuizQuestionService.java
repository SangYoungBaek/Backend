package com.starta.project.domain.quiz.service;

import com.starta.project.domain.quiz.dto.CreateQuestionRequestDto;
import com.starta.project.domain.quiz.dto.CreateQuizChoicesDto;
import com.starta.project.domain.quiz.dto.ShowQuestionResponseDto;
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

    public ResponseEntity<MsgResponse> createQuizQuestion(Long id, CreateQuestionRequestDto createQuestionRequestDto) {
        //퀴즈 찾기
        Quiz quiz = findQuiz(id);
        //문제 번호 찾기
        Integer questionNum = 0;
        //findTop == 가장 먼저 찾을 수 있는 항목
        Optional<QuizQuestion> question =  quizQuestionRepository.findTopByQuizOrderByQuestionNumDesc(quiz);
        if (question.isPresent()){
            questionNum = question.get().getQuestionNum();
        }
        //문제 만들기
        QuizQuestion quizQuestion = new QuizQuestion();
        questionNum++;
        quizQuestion.set(quiz,questionNum , createQuestionRequestDto.getTitle(), createQuestionRequestDto.getContent(),
                createQuestionRequestDto.getImage());
        quizQuestionRepository.save(quizQuestion);
        //선택지 만들기 [] 형식
        List<CreateQuizChoicesDto> quizChoicesList = createQuestionRequestDto.getQuizChoices();
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

    public ShowQuestionResponseDto showQuizQuestion(Long id, Integer questionNum) {
        // 퀴즈 찾기
        Quiz quiz = findQuiz(id);
        // 해당 퀴즈의 n번 문제 찾기
        QuizQuestion quizQuestion = quizQuestionRepository.findByQuizAndQuestionNum(quiz, questionNum);
        // 선택지 찾아오기
        List<QuizChoices> list = quizChoicesRepository.findAllByQuizQuestion(quizQuestion);
        // 반환
        ShowQuestionResponseDto showQuestionResponseDto = new ShowQuestionResponseDto();
        showQuestionResponseDto.set(quizQuestion, list);
        return showQuestionResponseDto;
    }

    // cascade를 사용하는 방식도 있지만 이용하기 위해서는 DB에 추가적 연관관계를 설정해야함
    // cascade와 같은 경우 강력한 기능이지만 생각 못한 상황에서 삭제될 가능성이 있기 때문에 그냥 단순 조회를 통해 찾아서 지움
    public MsgResponse deleteQuizQuestion(Long id, Integer questionNum) {
        // 퀴즈 찾기
        Quiz quiz = findQuiz(id);
        // 해당 퀴즈의 n번 문제 찾기
        QuizQuestion quizQuestion = quizQuestionRepository.findByQuizAndQuestionNum(quiz, questionNum);
        // 선택지 찾아오기
        List<QuizChoices> list = quizChoicesRepository.findAllByQuizQuestion(quizQuestion);
        // 삭제
        quizChoicesRepository.deleteAll(list);
        quizQuestionRepository.delete(quizQuestion);

        return new MsgResponse("삭제 성공");
    }

    public MsgResponse deleteChoices(Long id) {
        QuizChoices quizChoices = quizChoicesRepository.findById(id).orElseThrow( ()
         -> new NullPointerException("해당 선택지는 없는 선택지 입니다. "));
        quizChoicesRepository.delete(quizChoices);
        return new MsgResponse("선택지 삭제 성공! ");
    }

    private Quiz findQuiz(Long id) {
        return quizRepository.findById(id).orElseThrow( ()
        -> new NullPointerException("해당 퀴즈는 없는 선택지 입니다. "));
    }




    // 수정이라 주석 처리
//    public MsgResponse updateQuizQuestion(Long id, UpdateQuizQuestionDto updateQuizQuestionDto) {
//        QuizQuestion quizQuestion = quizQuestionRepository.findById(id).orElseThrow(()
//                -> new NullPointerException("해당 퀴즈는 없는 퀴즈 입니다. ")
//        );
//
//        quizQuestion.update(updateQuizQuestionDto);
//        quizQuestionRepository.save(quizQuestion);
//        MsgResponse msgResponse = new MsgResponse("문제 수정 성공! ");
//        return msgResponse;
//    }
}
