package com.starta.project.domain.quiz.repository;

import com.starta.project.domain.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findAllByCategoryOrderByCreatedAtDesc(String category);


}
