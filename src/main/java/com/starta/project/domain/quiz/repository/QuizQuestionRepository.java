package com.starta.project.domain.quiz.repository;

import com.starta.project.domain.quiz.entity.Quiz;
import com.starta.project.domain.quiz.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long> {



    Optional<QuizQuestion> findTopByQuizOrderByQuestionNumDesc(Quiz quiz);
}
