package com.starta.project.domain.quiz.repository;

import com.starta.project.domain.quiz.entity.QuizChoices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizChoicesRepository extends JpaRepository<QuizChoices, Long> {
}
