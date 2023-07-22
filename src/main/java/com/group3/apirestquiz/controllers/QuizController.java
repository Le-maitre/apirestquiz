package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.services.QuizService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Data
@RequestMapping("api/") // On va mapper les requêtes HTTP (GET, POST, PUT, DELETE, etc.) aux méthodes des contrôleurs. Elle indique quelle méthode doit être appelée lorsque certaines URL (chemins) sont atteintes par des requêtes HTTP spécifiques.
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("quizzes")
    public List<Quiz> getQuizzes(){
        return quizService.getQuizzes();
    }

    @GetMapping("quizzes/{quizId}")
    public Quiz getQuizById(@PathVariable Long quizId){
        return quizService.getQuizById(quizId);
    }

    @GetMapping(value = "quizzes", params = "domain")
    public List<Quiz> getQuizzesByDomain(@RequestParam("domain") String domain){
        return quizService.getQuizzesByDomain(domain);
    }
    @GetMapping(value = "quizzes", params = "title")
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
    public Quiz addQuiz(@RequestBody Quiz quiz, @PathVariable Long userId){
        quiz.setUser(new User());
        return quizService.addQuiz(quiz);
    }
}
