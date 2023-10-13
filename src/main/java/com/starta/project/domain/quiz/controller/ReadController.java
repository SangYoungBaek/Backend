package com.starta.project.domain.quiz.controller;

import com.starta.project.domain.quiz.dto.CategoryDto;
import com.starta.project.domain.quiz.dto.SimpleQuizDto;
import com.starta.project.domain.quiz.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReadController {

    private final ReadService readService;

    @PostMapping("/quiz/category")
    public ResponseEntity<List<SimpleQuizDto>> categoryList(@RequestBody CategoryDto categoryDto ) {
        return ResponseEntity.ok(readService.readByCategory(categoryDto));
    }

    @GetMapping("/quiz")
    public ResponseEntity<List<SimpleQuizDto>> recentlyList () {
        return ResponseEntity.ok(readService.readQuiz());
    }

    @GetMapping("/quiz/hot")
    public ResponseEntity<List<SimpleQuizDto>> hotQuizList () {
        return ResponseEntity.ok(readService.readQuizByHot());
    }


    @GetMapping("/quiz/viewNum")
    public ResponseEntity<List<SimpleQuizDto>> readByView () {
        return ResponseEntity.ok(readService.readByView());
    }
}
