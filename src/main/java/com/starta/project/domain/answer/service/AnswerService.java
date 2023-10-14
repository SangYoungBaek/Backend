package com.starta.project.domain.answer.service;

import com.starta.project.domain.answer.entity.MemberAnswer;
import com.starta.project.domain.answer.repository.MemberAnswerRepository;
import com.starta.project.domain.member.entity.Member;
import com.starta.project.domain.quiz.entity.QuizChoices;
import com.starta.project.domain.quiz.repository.QuizChoicesRepository;
import com.starta.project.domain.quiz.repository.QuizQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final MemberAnswerRepository memberAnswerRepository;
    private final QuizChoicesRepository quizChoicesRepository;

    @Transactional
    public void choice(Long id, Member member) {
        MemberAnswer memberAnswer = new MemberAnswer();
        QuizChoices quizChoices = quizChoicesRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 선택지는 잘못된 선택지입니다.! "));
        //정답 체크
        if (quizChoices.isChecks() == true) {
            memberAnswer.set();
        }
        //응답 저장
        memberAnswer.answer(member,quizChoices,quizChoices.getQuizQuestion());
        memberAnswerRepository.save(memberAnswer);
    }
}
