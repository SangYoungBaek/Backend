package com.starta.project.domain.mypage.service;

import com.starta.project.domain.member.entity.Member;
import com.starta.project.domain.mypage.dto.PurchaseHistoryItemDto;
import com.starta.project.domain.mypage.repository.PurchaseHistoryRepository;
import com.starta.project.global.messageDto.MsgDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    @Transactional(readOnly = true)
    public MsgDataResponse getPurchaseHistory(Member member) {
        return new MsgDataResponse("조회에 성공하셨습니다.", purchaseHistoryRepository.findByMemberDetailIdOrderByOrderedAtDesc(member.getId()).stream().map(PurchaseHistoryItemDto::new));
    }

}
