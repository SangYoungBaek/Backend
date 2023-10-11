package com.starta.project.domain.quiz.service;

import com.starta.project.domain.quiz.dto.CategoryDto;
import com.starta.project.domain.quiz.dto.SimpleQuizDto;
import com.starta.project.domain.quiz.entity.Quiz;
import com.starta.project.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ReadService {

    private final QuizRepository quizRepository;

    // 카테고리 별 정렬
    public List<SimpleQuizDto> readByCategory(CategoryDto categoryDto) {
        List<SimpleQuizDto> list = new ArrayList<>();
        List<Quiz> quizList = quizRepository.findAllByCategoryOrderByCreatedAtDesc(categoryDto.getCategory());
        for (Quiz quiz : quizList) {
            SimpleQuizDto simpleQuizDto = new SimpleQuizDto();
            simpleQuizDto.set(quiz);
            list.add(simpleQuizDto);
        }
        return list;
    }

    // 최신순
    public List<SimpleQuizDto> readQuiz() {
        List<SimpleQuizDto> list = new ArrayList<>();
        List<Quiz> quizList = quizRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        for (Quiz quiz : quizList) {
            SimpleQuizDto simpleQuizDto = new SimpleQuizDto();
            simpleQuizDto.set(quiz);
            list.add(simpleQuizDto);
        }
        return list;
    }
    //좋아요 순
    public List<SimpleQuizDto> readQuizByHot() {
        List<SimpleQuizDto> list = new ArrayList<>();
        List<Quiz> quizList = quizRepository.findAll(Sort.by(Sort.Direction.DESC, "likes"));
        for (Quiz quiz : quizList) {
            SimpleQuizDto simpleQuizDto = new SimpleQuizDto();
            simpleQuizDto.set(quiz);
            list.add(simpleQuizDto);
        }
        return list;
    }

    //조회순
    public List<SimpleQuizDto> readByView() {
        List<SimpleQuizDto> list = new ArrayList<>();
        List<Quiz> quizList = quizRepository.findAll(Sort.by(Sort.Direction.DESC, "viewCount"));
        for (Quiz quiz : quizList) {
            SimpleQuizDto simpleQuizDto = new SimpleQuizDto();
            simpleQuizDto.set(quiz);
            list.add(simpleQuizDto);
        }
        return list;
    }
}
