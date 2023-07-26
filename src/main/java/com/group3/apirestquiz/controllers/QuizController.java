package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.services.QuizService;
import com.group3.apirestquiz.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/") // On va mapper les requêtes HTTP (GET, POST, PUT, DELETE, etc.) aux méthodes des contrôleurs. Elle indique quelle méthode doit être appelée lorsque certaines URL (chemins) sont atteintes par des requêtes HTTP spécifiques.
public class QuizController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private UserService userService;

    @Operation(summary = "Obtenir la liste des quiz")
    @GetMapping("quizzes")
    public List<Quiz> getQuizzes(){
        return quizService.getQuizzes();
    }

    @Operation(summary = "Obtenir un quiz spécifique par son ID")
    @GetMapping("quizzes/{quizId}")
    public Optional<Quiz> getQuizById(@PathVariable Long quizId){
        return quizService.getQuizById(quizId);
    }

    @Operation(summary = "Obtenir des quiz en fonction du domaine de connaissance")
    @GetMapping(value = "quizzes", params = "domain")
    public List<Quiz> getQuizzesByDomain(@RequestParam("domain") String domain){
        return quizService.getQuizzesByDomain(domain);
    }

    @Operation(summary = "Obtenir des quiz en fonction du titre")
    @GetMapping(value = "quizzes", params = "title", produces = MediaType.APPLICATION_JSON_VALUE /*MediaType.APPLICATION_JSON_VALUE Permet d'acceper des espace entre les chaines. Exemple: Langage Java. Parce que les url n'accepte pas les espaces par defaut*/)
    public List<Quiz> getQuizzesByTitle(@RequestParam("title") String title){
        return quizService.getQuizzesByTitle(title);
    }

    @Operation(summary = "Obtenir des quiz en fonction du nombre maximal de question")
    @GetMapping(value = "quizzes", params = "nbMaxQuestion")
    public List<Quiz> getQuizzesByNbMaxQuestion(@RequestParam("nbMaxQuestion") int nbMaxQuestion){
        return quizService.getQuizzesByNbMaxQuestion(nbMaxQuestion);
    }

    @Operation(summary = "Obtenir des quiz en fonction de la visibilité")
    @GetMapping(value = "quizzes", params = "visibility")
    public List<Quiz> getQuizzesByVisibility(@RequestParam("visibility") String visibility){
        return quizService.getQuizzesByVisibility(visibility);
    }

    @Operation(summary = "Obtenir des quiz en fonction de la date de création")
    @GetMapping(value = "quizzes", params = "creationDate")
    public List<Quiz> getQuizzesByCreationDate(@RequestParam("creationDate") Date creationDate){
        return quizService.getQuizzesByCreationDate(creationDate);
    }

    @Operation(summary = "Obtenir les quiz pour un utilisateur")
    @GetMapping("users/{userId}/quizzes")
    public List<Quiz> getQuizzesByUser(@PathVariable Long userId){
        return quizService.getQuizzesByUserId(userId);
    }

    @Operation(summary = "Obtenir un quiz pour un utilisateur")
    @GetMapping("users/{userId}/quizzes/{quizId}")
    public Optional<Quiz> getQuizByUserIdAndQuizId(@PathVariable Long userId, @PathVariable Long quizId){
        return quizService.getQuizByUserUserIdAndQuizId(userId, quizId);
    }

    @Operation(summary = "Mise à jour complete des données d'un quiz pour un utilisateur")
    @PutMapping("users/{userId}/quizzes/{quizId}")
    public Optional<Quiz> updateWithPutValueQuiz(@PathVariable Long userId, @PathVariable Long quizId, @RequestBody Quiz newQuiz) {
        return quizService.updateWithPutValueQuiz(userId, quizId, newQuiz);
    }

    @Operation(summary = "Création d'un quiz pour un utilisateur")
    @PostMapping("users/{userId}/quizzes")
    public Quiz addQuiz(@RequestBody @Valid Quiz quiz, @PathVariable Long userId){
        quiz.setUser(userService.getUserById(userId).get());
        return quizService.addQuiz(quiz);
    }

    @Operation(summary = "Suppression d'un quiz d'un utilisateur")
    @DeleteMapping("users/{userId}/quizzes/{quizId}")
    public void deleteQuiz(@PathVariable Long userId, @PathVariable Long quizId) {
        quizService.deleteQuiz(userId, quizId);
    }

    @Operation(summary = "Mise à jour partielle des données d'un quiz pour un utilisateur")
    @RequestMapping(value = "users/{userId}/quizzes/{quizId}", method = RequestMethod.PATCH)
    public ResponseEntity<Optional<Quiz>> updateWithPathValueQuiz(@PathVariable Long userId, @PathVariable Long quizId, @RequestBody Map<String, Object> updateQuiz) {
        return quizService.updateWithPathValueQuiz(userId, quizId, updateQuiz);
    }
}
