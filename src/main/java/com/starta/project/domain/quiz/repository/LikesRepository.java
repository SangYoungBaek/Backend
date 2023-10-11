package com.starta.project.domain.quiz.repository;

import com.starta.project.domain.member.entity.Member;
import com.starta.project.domain.quiz.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMember(Member member);
}
