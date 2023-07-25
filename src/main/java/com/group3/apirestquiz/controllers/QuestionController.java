package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Question;
import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.services.QuestionService;
import com.group3.apirestquiz.services.QuizService;
import com.group3.apirestquiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("questions")
    public List<Question> getQuestions(){
        return questionService.getQuestions();
    }
    @PostMapping("users/{userId}/quizzes/{quizId}/questions") // une question doit obligatoire appartenir à un quiz qui lui appartient à un utilisateur
    public Question addQuestion(@RequestBody Question question, @PathVariable Long userId, @PathVariable Long quizId){
       return questionService.addQuestion(question, userId, quizId);
    }
    @GetMapping("question/{questionId}")
    public Optional<Question> getQuestionById(@PathVariable Long questionId){
        return questionService.getQuestionById(questionId);
    }
    @GetMapping("users/{userId}/quizzes/{quizId}/questions")
    public List<Question> getQuestionsByQuizIdAndUserId(@PathVariable Long userId, @PathVariable Long quizId){
        return questionService.getQuestionsByQuizIdAndUserId(quizId, userId);
    }
    @GetMapping(value = "questions", params = {"userId", "quizId"})
    public List<Question> getQuestionsByQuizIdAndUserId2(@RequestParam("userId") Long userId, @RequestParam("quizId") Long quizId){
        return questionService.getQuestionsByQuizIdAndUserId(quizId, userId);
    }
    @GetMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}")
    public Optional<Question> getQuetionsByQuizIdAndUserId(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId){
        return questionService.getQuestionByQuizIdAndUserId(quizId, userId, questionId);
    }
    @DeleteMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}")
    public void deleteQuestion(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId) {
        questionService.deleteQuestion(userId, quizId, questionId);
    }
    @GetMapping(value = "questions", params = {"userId", "quizId", "questionId"})
    public Optional<Question> getQuetionsByQuizIdAndUserId2(@RequestParam("userId") Long userId, @RequestParam("quizId") Long quizId, @RequestParam("questionId") Long questionId){
        return questionService.getQuestionByQuizIdAndUserId(quizId, userId, questionId);
    }

    @GetMapping(value = "question", params = "point")
    public List<Question> getQuestionByPoint(@RequestParam("point") int point){
        return questionService.getQuestionByPoint(point);
    }
    @GetMapping(value = "question", params = "type")
    public List<Question> getQuestionByType(@RequestParam("type") String type){
        return questionService.getQuestionByType(type);
    }
    @GetMapping(value = "question", params = "text")
    public List<Question> getQuestionByText(@RequestParam("text") String text){
        return questionService.getQuestionByText(text);
    }
    @GetMapping(value = "question", params = "numResponse")
    public List<Question> getQuestionByNumResponse(@RequestParam("numResponse") int numResponse){
        return questionService.getQuestionByNumResponse(numResponse);
    }
    @GetMapping(value = "question", params = "questionId")
    public Optional<Question> getQuestionById2(@RequestParam("questionId") Long questionId){
        return questionService.getQuestionById(questionId);
    }
}
