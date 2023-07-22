package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.services.QuizService;
import com.group3.apirestquiz.services.UserService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/") // On va mapper les requêtes HTTP (GET, POST, PUT, DELETE, etc.) aux méthodes des contrôleurs. Elle indique quelle méthode doit être appelée lorsque certaines URL (chemins) sont atteintes par des requêtes HTTP spécifiques.
public class QuizController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private UserService userService;

    @GetMapping("quizzes")
    public List<Quiz> getQuizzes(){
        return quizService.getQuizzes();
    }

    @GetMapping("quizzes/{quizId}")
    public Optional<Quiz> getQuizById(@PathVariable Long quizId){
        return quizService.getQuizById(quizId);
    }

    @GetMapping(value = "quizzes", params = "domain")
    public List<Quiz> getQuizzesByDomain(@RequestParam("domain") String domain){
        return quizService.getQuizzesByDomain(domain);
    }
    @GetMapping(value = "quizzes", params = "title", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Quiz> getQuizzesByTitle(@RequestParam("title") String title){
        return quizService.getQuizzesByTitle(title);
    }
    @GetMapping(value = "quizzes", params = "nbMaxQuestion")
    public List<Quiz> getQuizzesByNbMaxQuestion(@RequestParam("nbMaxQuestion") int nbMaxQuestion){
        return quizService.getQuizzesByNbMaxQuestion(nbMaxQuestion);
    }
    @GetMapping(value = "quizzes", params = "visibility")
    public List<Quiz> getQuizzesByVisibility(@RequestParam("visibility") String visibility){
        return quizService.getQuizzesByVisibility(visibility);
    }
    @GetMapping(value = "quizzes", params = "creationDate")
    public List<Quiz> getQuizzesByCreationDate(@RequestParam("creationDate") Date creationDate){
        return quizService.getQuizzesByCreationDate(creationDate);
    }

    @GetMapping("users/{userId}/quizzes")
    public List<Quiz> getQuizzesByUser(@PathVariable Long userId){
        return quizService.getQuizzesByUserId(userId);
    }

    @GetMapping("users/{userId}/quizzes/{quizId}")
    public Optional<Quiz> getQuizByUserIdAndQuizId(@PathVariable Long userId, @PathVariable Long quizId){
        return quizService.getQuizByUserUserIdAndQuizId(userId, quizId);
    }

        @PostMapping("users/{userId}/quizzes")
    public Quiz addQuiz(@RequestBody @Valid Quiz quiz, @PathVariable Long userId){
        quiz.setUser(userService.getUserById(userId).get());
        return quizService.addQuiz(quiz);
    }
}
