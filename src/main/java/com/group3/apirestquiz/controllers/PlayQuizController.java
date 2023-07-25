package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Question;
import com.group3.apirestquiz.models.Result;
import com.group3.apirestquiz.services.QuestionService;
import com.group3.apirestquiz.services.ResultService;
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
    @GetMapping("play")
    public Optional<Question> getNextQuestion(@PathVariable Long userId, @PathVariable Long quizId) {
       return questionService.getNextQuestion(userId, quizId);
    }

    @GetMapping("end")
    public Optional<Result> endGame(@PathVariable Long userId, @PathVariable Long quizId){
        return resultService.endGame(userId, quizId);
    }
    @GetMapping(value = "play", params = "answer")
    public Result respondQuestion(@PathVariable Long userId, @PathVariable Long quizId, @RequestParam("answer") int answer) {
        return resultService.respondQuestion(userId, quizId, answer);
    }
    @GetMapping("rank")
    public ResponseEntity<Map<Integer, Map<String, String>>> getRank(@PathVariable Long userId, @PathVariable Long quizId) {
        Map<Integer, Map<String, String>> maxScoreResults = resultService.getMaxScoreResultsByUserAndQuiz(quizId);
        return ResponseEntity.ok(maxScoreResults);
    }
}
