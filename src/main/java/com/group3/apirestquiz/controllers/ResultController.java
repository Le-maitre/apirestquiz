package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Result;
import com.group3.apirestquiz.services.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Operation(summary = "Obténir les résultats qu'un utilisateur à obtenue dans un quiz")
    @GetMapping("users/{userId}/quizzes/{quizId}/results")
    public List<Result> getResultsByUserIdAndQuizId(@PathVariable Long userId, @PathVariable Long quizId) {
        return resultService.getResultsByUserIdAndQuizId(userId, quizId);
    }
    @Operation(summary = "Obtenir tous les résultats qu'un utilisateur spécifique a obtenu")
    @GetMapping("users/{userId}/results")
    public List<Result> getResultsByUserId(@PathVariable Long userId) {
        return resultService.getResultsByUserId(userId);
    }

    @Operation(summary = "Obtenir un résultat spécifique qu'un utilisateur à obtenue dans un quiz")
    @GetMapping("users/{userId}/quizzes/{quizId}/results/{resultId}")
    public Optional<Result> getResultByUserIdAndQuizId(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long resultId) {
        return resultService.getResultByUserIdAndQuizId(userId, quizId, resultId);
    }

    @Operation(summary = "Supprimer un résultat qu'un utilisateur à obtenu dans un quiz")
    @DeleteMapping("users/{userId}/quizzes/{quizId}/results/{resultId}")
    public void deleteResult(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long resultId){
        resultService.deleteResult(userId, quizId, resultId);
    }

    @Operation(summary = "Obtenir tous les résultats dans un quiz")
    @GetMapping("quizzes/{quizId}/results")
    public List<Result> getResultsByQuizId(@PathVariable Long quizId) {
        return resultService.getResultsByQuizId(quizId);
    }

    @Operation(summary = "Obtenir un résultat spécifique dans un quiz")
    @GetMapping("quizzes/{quizId}/resultats/{resultId}")
    public Optional<Result> getResultByQuizId(@PathVariable Long quizId, @PathVariable Long resultId) {
        return resultService.getResultByQuizId(quizId, resultId);
    }

    // On cherche les resultat d'un quiz à travers un score données
    @Operation(summary = "Obtenir la liste des résultats dans un quiz en fonction d'un score donné")
    @GetMapping(value = "quizzes/{quizId}/results", params = "score")
    public List<Result> getResultsByQuizIdAndScore(@PathVariable Long quizId, @RequestParam("score") int score) {
        return resultService.getResultsByQuizIdAndScore(quizId, score);
    }
}
