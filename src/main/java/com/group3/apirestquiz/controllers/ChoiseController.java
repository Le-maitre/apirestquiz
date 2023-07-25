package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Choise;
import com.group3.apirestquiz.models.Question;
import com.group3.apirestquiz.services.ChoiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class ChoiseController {

    @Autowired
    private ChoiseService choiseService;

    @GetMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}/choises")
    public List<Choise> getChoisesByQuizIdAndQuestionId(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId) {
        return choiseService.getChoisesByQuizIdAndQuestionId(userId, quizId, questionId);
    }
    @GetMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}/choises/{choiseId}")
    public Choise getChoiseByQuizIdAndQuestionId(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId, @PathVariable Long choiseId) {
        return choiseService.getChoiseByUserIdAndQuizIdAndQuestionIdAndChoiseId(userId, quizId, questionId, choiseId);
    }
    @PostMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}/choises")
    public Choise addChoise(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId, @RequestBody Choise choise) {
        return choiseService.addChoise(userId, quizId, questionId, choise);
    }
}
