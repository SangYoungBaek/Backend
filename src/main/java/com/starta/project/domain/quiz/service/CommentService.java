package com.starta.project.domain.quiz.service;

import com.starta.project.domain.member.entity.Member;
import com.starta.project.domain.member.repository.MemberRepository;
import com.starta.project.domain.quiz.dto.CreateCommentRequestDto;
import com.starta.project.domain.quiz.dto.UpdateCommentResponseDto;
import com.starta.project.domain.quiz.entity.Comment;
import com.starta.project.domain.quiz.entity.Quiz;
import com.starta.project.domain.quiz.repository.CommentRepository;
import com.starta.project.domain.quiz.repository.QuizRepository;
import com.starta.project.global.messageDto.MsgDataResponse;
import com.starta.project.global.messageDto.MsgResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final QuizRepository quizRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public MsgResponse createComment(CreateCommentRequestDto createCommentRequestDto) {
        Quiz quiz = quizRepository.findById(createCommentRequestDto.getId()).orElseThrow( ()
        -> new NullPointerException("해당 퀴즈가 없습니다. "));
        Member member = memberRepository.findById(1L).orElseThrow( () ->
                new NullPointerException("해당 멤버는 없는 멤버 입니다. "));
        Comment comment = new Comment();
        comment.set(quiz,createCommentRequestDto,member);
        commentRepository.save(comment);
        return new MsgResponse("댓글 작성을 성공했습니다");
    }


    public MsgResponse updateComment(Long id, UpdateCommentResponseDto updateCommentResponseDto) {
        Comment comment = findComment(id);
        comment.update(updateCommentResponseDto.getContent());
        commentRepository.save(comment);
        return new MsgResponse("댓글 수정을 성공했습니다. ");
    }

    public MsgResponse deleteComment(Long id) {
        Comment comment = findComment(id);
        commentRepository.delete(comment);
        return new MsgResponse("댓글 삭제를 성공했습니다. ");
    }

    private Comment findComment (Long id) {
        return commentRepository.findById(id).orElseThrow( () ->
                new NullPointerException("해당 댓글이 없습니다. "));
    }
}
