package com.starta.project.domain.mypage.repository;

import com.starta.project.domain.mypage.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {

    List<PurchaseHistory> findByMemberDetailIdOrderByOrderedAtDesc(Long memberDetailId);

}