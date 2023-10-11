package com.starta.project.domain.quiz.service;

import com.starta.project.domain.member.entity.Member;
import com.starta.project.domain.member.repository.MemberRepository;
import com.starta.project.domain.quiz.dto.CreateQuizRequestDto;
import com.starta.project.domain.quiz.dto.CreateQuizResponseDto;
import com.starta.project.domain.quiz.dto.ShowQuizResponseDto;
import com.starta.project.domain.quiz.entity.*;
import com.starta.project.domain.quiz.repository.*;
import com.starta.project.global.messageDto.MsgDataResponse;
import com.starta.project.global.messageDto.MsgResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizChoicesRepository quizChoicesRepository;
    private final LikesRepository likesRepository;

    //퀴즈 만들기
    public ResponseEntity<MsgDataResponse> createQuiz(CreateQuizRequestDto quizRequestDto, Member member) {
        Quiz quiz = new Quiz();
        //생성시간
        LocalDateTime now = LocalDateTime.now();
        //퀴즈 생성
        quiz.set(quizRequestDto, now, member);
        quizRepository.save(quiz);
        //퀴즈 반환
        CreateQuizResponseDto quizResponseDto = new CreateQuizResponseDto();
        quizResponseDto.set(quiz.getId());
        MsgDataResponse msgDataResponse = new MsgDataResponse("퀴즈 생성 성공!" , quizRequestDto);
        return ResponseEntity.status(200).body(msgDataResponse);
    }

    public ResponseEntity<ShowQuizResponseDto> showQuiz(Long id) {
        ShowQuizResponseDto showQuizResponseDto = new ShowQuizResponseDto();
        Quiz quiz = findQuiz(id);
        //댓글 가져오기
        List<Comment> comments = getComment(quiz.getId());
        //조회수 => api 검색 = 조회하는 횟수 -> 이거 조회 api 안해도 될꺼 같은데..?
        // 만약 할꺼면 여기다 동시성 제어를 걸어야 할거 같습니다!
        Integer viewCount = quiz.getViewCount();
        viewCount++;
        quiz.view(viewCount);
        quizRepository.save(quiz);
        //반환하는 데이터
        showQuizResponseDto.set(quiz,viewCount,comments);

        return ResponseEntity.status(200).body(showQuizResponseDto);
    }

    public MsgResponse deleteQuiz(Long id, Member member) {
        //이전의 것과 마찬가지 입니다.
        Quiz quiz = findQuiz(id);
        List<Comment> comments = getComment(id);
        List<QuizQuestion> quizQuestionList = quizQuestionRepository.findAllByQuiz(quiz);
        List<QuizChoices> quizChoicesList = new ArrayList<>();
        for (QuizQuestion quizQuestion : quizQuestionList) {
            List<QuizChoices> quizChoices = quizChoicesRepository.findAllByQuizQuestion(quizQuestion);
            quizChoicesList.addAll(quizChoices);
        }
        // 여기도 마찬가지로 효율이 좋다고하네요? (테스트 결과 문제수 22개, 문항 수 44개 before 1199ms | after 139 ms)
        commentRepository.deleteAllInBatch(comments);
        quizChoicesRepository.deleteAllInBatch(quizChoicesList);
        quizQuestionRepository.deleteAllInBatch(quizQuestionList);
        quizRepository.delete(quiz);

        return new MsgResponse("퀴즈 삭제 성공! ");
    }

    public MsgResponse pushLikes(Long id, Member member) {
        Quiz quiz = findQuiz(id);
        Integer likesNum = quiz.getLikes();
        if (likesRepository.findByMember(member).isPresent()){
            likesNum--;
            if(likesNum <= 0 ) likesNum = 0;
            quiz.pushLikes(likesNum);
            likesRepository.delete(likesRepository.findByMember(member).get());
            return new MsgResponse("좋아요를 취소했습니다! ");
        }

        Likes likes = new Likes();
        likes.set(quiz,member);
        likesRepository.save(likes);
        likesNum++;
        quiz.pushLikes(likesNum);
        return new MsgResponse("좋아요를 눌렀습니다. ");
    }


    private Quiz findQuiz (Long id) {
       return quizRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 퀴즈가 없습니다."));
    }

    private List<Comment> getComment(Long id) {
        List<Comment> commentList = commentRepository.findAllByQuizId(id);
        return commentList;
    }



    //수정이기 때문에 주석 처리
//    public MsgResponse update(Long id, CreateQuizRequestDto quizRequestDto) {
//        Quiz quiz = findQuiz(id);
//        quiz.update(quizRequestDto);
//        quizRepository.save(quiz);
//        MsgResponse msgResponse = new MsgResponse("문제를 수정하셨습니다.");
//        return msgResponse;
//    }

}
