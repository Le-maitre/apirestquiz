package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Question;
import com.group3.apirestquiz.services.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Operation(summary = "Obtenir la liste des questions")
    @GetMapping("questions")
    public List<Question> getQuestions(){
        return questionService.getQuestions();
    }

    @Operation(summary = "Création d'une question pour un quiz d'un utilisateur")
    @PostMapping("users/{userId}/quizzes/{quizId}/questions") // une question doit obligatoire appartenir à un quiz qui lui appartient à un utilisateur
    public Question addQuestion(@RequestBody Question question, @PathVariable Long userId, @PathVariable Long quizId){
       return questionService.addQuestion(question, userId, quizId);
    }

    @Operation(summary = "Obtenir une question spécifique")
    @GetMapping("questions/{questionId}")
    public Optional<Question> getQuestionById(@PathVariable Long questionId){
        return questionService.getQuestionById(questionId);
    }

    @Operation(summary = "Obtenir les questions d'un quiz d'un utilisateur")
    @GetMapping("users/{userId}/quizzes/{quizId}/questions")
    public List<Question> getQuestionsByQuizIdAndUserId(@PathVariable Long userId, @PathVariable Long quizId){
        return questionService.getQuestionsByQuizIdAndUserId(quizId, userId);
    }

    @Operation(summary = "Obtenir les questions d'un quiz d'un utilisateur")
    @GetMapping(value = "questions", params = {"userId", "quizId"})
    public List<Question> getQuestionsByQuizIdAndUserId2(@RequestParam("userId") Long userId, @RequestParam("quizId") Long quizId){
        return questionService.getQuestionsByQuizIdAndUserId(quizId, userId);
    }

    @Operation(summary = "Obtenir une question d'un quiz d'un utilisateur")
    @GetMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}")
    public Optional<Question> getQuetionsByQuizIdAndUserId(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId){
        return questionService.getQuestionByQuizIdAndUserId(quizId, userId, questionId);
    }

    @Operation(summary = "Supprimer une question d'un quiz d'un utilisateur")
    @DeleteMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}")
    public void deleteQuestion(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId) {
        questionService.deleteQuestion(userId, quizId, questionId);
    }

    @Operation(summary = "Obtenir une question d'un quiz d'un utilisateur")
    @GetMapping(value = "questions", params = {"userId", "quizId", "questionId"})
    public Optional<Question> getQuetionsByQuizIdAndUserId2(@RequestParam("userId") Long userId, @RequestParam("quizId") Long quizId, @RequestParam("questionId") Long questionId){
        return questionService.getQuestionByQuizIdAndUserId(quizId, userId, questionId);
    }

    @Operation(summary = "Obtenir les questions par point")
    @GetMapping(value = "questions", params = "point")
    public List<Question> getQuestionsByPoint(@RequestParam("point") int point){
        return questionService.getQuestionsByPoint(point);
    }

    @Operation(summary = "Obtenir les questions par type")
    @GetMapping(value = "questions", params = "type")
    public List<Question> getQuestionsByType(@RequestParam("type") String type){
        return questionService.getQuestionsByType(type);
    }

    @Operation(summary = "Obtenir les questions par text")
    @GetMapping(value = "questions", params = "text")
    public List<Question> getQuestionsByText(@RequestParam("text") String text){
        return questionService.getQuestionsByText(text);
    }

    @Operation(summary = "Obtenir les questions par numResponse (numéro de la réponse)")
    @GetMapping(value = "questions", params = "numResponse")
    public List<Question> getQuestionsByNumResponse(@RequestParam("numResponse") int numResponse){
        return questionService.getQuestionsByNumResponse(numResponse);
    }

    @Operation(summary = "Obtenir les questions par ID")
    @GetMapping(value = "questions", params = "questionId")
    public Optional<Question> getQuestionsById2(@RequestParam("questionId") Long questionId){
        return questionService.getQuestionById(questionId);
    }


    @Operation(summary = "Mise à jour complete des données d'un question pour un quiz d'un utilisateur")
    @PutMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}")
    public Optional<Question> updateWithPutQuestion(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId, @RequestBody Question newQuestion) {
        return questionService.updateWithPutValueQuestion(userId, quizId, questionId, newQuestion);
    }

    @Operation(summary = "Mise à jour partielle des données d'un question pour un quiz d'un utilisateur")
    @RequestMapping(value = "users/{userId}/quizzes/{quizId}/questions/{questionId}", method = RequestMethod.PATCH)
    public ResponseEntity<Optional<Question>> updateWithPathValueQuiz(@PathVariable Long userId, @PathVariable Long quizId , @PathVariable Long questionId, @RequestBody Map<String, Object> updateQuestion) {
        return questionService.updateWithPathValueQuiz(userId, quizId, questionId, updateQuestion);
    }
}
