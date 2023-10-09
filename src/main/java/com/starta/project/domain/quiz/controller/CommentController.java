package com.starta.project.domain.quiz.controller;

import com.starta.project.domain.quiz.dto.CreateCommentRequestDto;
import com.starta.project.domain.quiz.dto.UpdateCommentResponseDto;
import com.starta.project.domain.quiz.service.CommentService;
import com.starta.project.global.messageDto.MsgDataResponse;
import com.starta.project.global.messageDto.MsgResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<MsgResponse> createComment (@RequestBody CreateCommentRequestDto createCommentRequestDto) {
        return ResponseEntity.ok(commentService.createComment(createCommentRequestDto));
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<MsgResponse> updateComment (@PathVariable Long id ,
                                                      @RequestBody UpdateCommentResponseDto updateCommentResponseDto){
        return ResponseEntity.ok(commentService.updateComment(id, updateCommentResponseDto));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<MsgResponse> deleteComment (@PathVariable Long id) {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }
}
