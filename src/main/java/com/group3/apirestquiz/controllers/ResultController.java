package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Result;
import com.group3.apirestquiz.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("users/{userId}/quizzes/{quizId}/results")
    public List<Result> getResultsByUserIdAndQuizId(@PathVariable Long userId, @PathVariable Long quizId) {
        return resultService.getResultsByUserIdAndQuizId(userId, quizId);
    }
    @GetMapping("users/{userId}/quizzes/{quizId}/results/{resultId}")
    public Optional<Result> getResultByUserIdAndQuizId(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long resultId) {
        return resultService.getResultByUserIdAndQuizId(userId, quizId, resultId);
    }
    @GetMapping("quizzes/{quizId}/results")
    public List<Result> getResultsByQuizId(@PathVariable Long quizId) {
        return resultService.getResultsByQuizId(quizId);
    }
    @GetMapping("quizzes/{quizId}/resultats/{resultId}")
    public Optional<Result> getResultByQuizId(@PathVariable Long quizId, @PathVariable Long resultId) {
        return resultService.getResultByQuizId(quizId, resultId);
    }

    // On cherche les resultat d'un quiz à travers un score données
    @GetMapping(value = "quizzes/{quizId}/results", params = "score")
    public List<Result> getResultsByQuizIdAndScore(@PathVariable Long quizId, @RequestParam("score") int score) {
        return resultService.getResultsByQuizIdAndScore(quizId, score);
    }
}
