package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Question;
import com.group3.apirestquiz.models.Result;
import com.group3.apirestquiz.services.QuestionService;
import com.group3.apirestquiz.services.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/users/{userId}/quizzes/{quizId}/")
public class PlayQuizController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private ResultService resultService;

    @Operation(summary = "Lancement du quiz")
    @GetMapping(value = "play")
    public Optional<Question> getNextQuestion(@PathVariable Long userId, @PathVariable Long quizId) {
       return questionService.getNextQuestion(userId, quizId);
    }

    @Operation(summary = "Terminer un quiz")
    @GetMapping("end")
    public Optional<Result> endGame(@PathVariable Long userId, @PathVariable Long quizId){
        return resultService.endGame(userId, quizId);
    }

    @Operation(summary = "Repondre à un quiz")
    @GetMapping(value = "play", params = "answer")
    public Result respondQuestion(@PathVariable Long userId, @PathVariable Long quizId, @RequestParam("answer") int answer) {
        return resultService.respondQuestion(userId, quizId, answer);
    }


}
