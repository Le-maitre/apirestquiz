package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.services.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Obtenir la liste des utilisateurs")
    @GetMapping("users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @Operation(summary = "Créer un nouveau utilisateur")
    @PostMapping("users")
    public User addUser(@Valid @RequestBody User user){
        return userService.addUser(user);
    }

    @Operation(summary = "Obtenir un utilisateur spécifique")
    @GetMapping("users/{userId}")
    public Optional<User> getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @Operation(summary = "Obtenir les users qui on jouer à un quiz donné")
    @GetMapping("quizzes/{quizId}/users/played")
    public List<User> getUsersPlayedInQuiz(@PathVariable Long quizId){
        return userService.getUsersPlayedInQuiz(quizId);
    }

    @Operation(summary = "Supprimer un utilisateur spécifique")
    @DeleteMapping("users/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @Operation(summary = "Faire une mise à jour complete d'un utilisateur")
    @PutMapping("users/{userId}")
    public Optional<User> updateWithPutValueUser(@PathVariable Long userId, @RequestBody User user) {
        return userService.updateWithPutValueUser(userId, user);
    }

    @Operation(summary = "Connecter un utilisateur avec le login et le password")
    @PostMapping("users/connect") // l'utilisateur renseigne dans le corps un objet JSON contenant le login et le password
    public Optional<User> getUserByLoginAndPassword(@Valid @RequestBody Map<String, String> requestBody){
        String login = requestBody.get("login");
        String password = requestBody.get("password");
        return userService.getUserByLoginAndPassword(login, password);
    }

    @Operation(summary = "Faire une mise à jour partielle d'un utilisateur")
    @RequestMapping(value = "users/{userId}", method = RequestMethod.PATCH)
    public ResponseEntity<Optional<User>> updateWithPathValueUser(@PathVariable Long userId, @RequestBody Map<String, String> updateUser) {
        return userService.updateWithPathValueUser(userId, updateUser);
   }
}
