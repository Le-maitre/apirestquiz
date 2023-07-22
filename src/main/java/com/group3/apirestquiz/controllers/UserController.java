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
    @PostMapping("users/connect")
    public Optional<User> getUserByLoginAndPassword(@RequestBody Map<String, String> requestBody){
        String login = requestBody.get("login");
        String password = requestBody.get("password");
        return userService.getUserByLoginAndPassword(login, password);
    }
}
