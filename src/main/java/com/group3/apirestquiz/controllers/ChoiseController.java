package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Choise;
import com.group3.apirestquiz.services.ChoiseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class ChoiseController {

    @Autowired
    private ChoiseService choiseService;

    @Operation(summary = "Obtenir les choix d'une question pour un quiz d'un utilisateur")
    @GetMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}/choises")
    public List<Choise> getChoisesByQuizIdAndQuestionId(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId) {
        return choiseService.getChoisesByQuizIdAndQuestionId(userId, quizId, questionId);
    }

    @Operation(summary = "Obtenir un choix spécifique dans une question d'un quiz pour un utilisateur")
    @GetMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}/choises/{choiseId}")
    public Optional<Choise> getChoiseByQuizIdAndQuestionId(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId, @PathVariable Long choiseId) {
        return choiseService.getChoiseByUserIdAndQuizIdAndQuestionIdAndChoiseId(userId, quizId, questionId, choiseId);
    }

    @Operation(summary = "Supprimer un choix spécifique dans une question d'un quiz pour un utilisateur")
    @DeleteMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}/choises/{choiseId}")
    public void deleteChoise(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId, @PathVariable Long choiseId) {
        choiseService.deleteChoise(userId, quizId, questionId, choiseId);
    }

    @Operation(summary = "Créer un choix spécifique dans une question d'un quiz pour un utilisateur")
    @PostMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}/choises")
    public Choise addChoise(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId, @Valid @RequestBody Choise choise) {
        return choiseService.addChoise(userId, quizId, questionId, choise);
    }

    @Operation(summary = "Mise à jour complete d'un choix spécifique dans une question d'un quiz pour un utilisateur")
    @PutMapping("users/{userId}/quizzes/{quizId}/questions/{questionId}/choises/{choiseId}")
    public Optional<Choise> updateWithPutValueChoise(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId, @PathVariable Long choiseId, @RequestBody Choise newChoise) {
        return choiseService.updateWithPutValueChoise(userId, quizId, questionId, choiseId, newChoise);
    }

    @Operation(summary = "Mise à jour partielle d'un choix spécifique dans une question d'un quiz pour un utilisateur")
    @RequestMapping(value = "users/{userId}/quizzes/{quizId}/questions/{questionId}/choises/{choiseId}", method = RequestMethod.PATCH)
    public ResponseEntity<Optional<Choise>> updateWithPathValueQuiz(@PathVariable Long userId, @PathVariable Long quizId, @PathVariable Long questionId, @PathVariable Long choiseId, @RequestBody Map<String, Object> updateChoise) {
        return choiseService.updateWithPathValueQuiz(userId, quizId, questionId, choiseId, updateChoise);
    }

}
