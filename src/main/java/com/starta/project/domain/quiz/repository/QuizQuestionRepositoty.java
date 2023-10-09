package com.starta.project.domain.quiz.repository;

import com.starta.project.domain.quiz.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepositoty extends JpaRepository<QuizQuestion,Long> {
}
