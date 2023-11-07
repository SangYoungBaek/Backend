package com.starta.project.domain.liveQuiz.service;


import com.starta.project.domain.liveQuiz.dto.ChatMessageDto;
import com.starta.project.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LiveQuizService {


    public ChatMessageDto sendMessage(ChatMessageDto chatMessage) {
        if (chatMessage != null && chatMessage.getMessage() != null) {
            String escapedMessage = HtmlUtils.htmlEscape(chatMessage.getMessage());
            chatMessage = new ChatMessageDto(1L, "민지귀요미", escapedMessage, LocalDateTime.now());
        }
        return chatMessage;
    }
}
