package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.services.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public List<User> getUsers(){
        return userService.getUsers();
    }
    @PostMapping("users")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }
    @GetMapping("users/{userId}")
    public Optional<User> getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }
    @DeleteMapping("users/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
    @PutMapping("users/{userId}")
    public Optional<User> updateWithPutValueUser(@PathVariable Long userId, @RequestBody User user) {
        return userService.updateWithPutValueUser(userId, user);
    }
    @PostMapping("users/connect") // l'utilisateur renseigne dans le corps un objet JSON contenant le login et le password
    public Optional<User> getUserByLoginAndPassword(@RequestBody Map<String, String> requestBody){
        String login = requestBody.get("login");
        String password = requestBody.get("password");
        return userService.getUserByLoginAndPassword(login, password);
    }
}
