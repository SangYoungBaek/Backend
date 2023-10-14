package com.starta.project.domain.answer.repository;

import com.starta.project.domain.answer.entity.MemberAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAnswerRepository extends JpaRepository<MemberAnswer,Long> {
}
