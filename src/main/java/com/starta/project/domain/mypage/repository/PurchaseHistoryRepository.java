package com.starta.project.domain.mypage.repository;

import com.starta.project.domain.mypage.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
}