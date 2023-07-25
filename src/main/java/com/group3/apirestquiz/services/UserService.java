package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User addUser(User user){
        return userRepository.save(user);
    }
    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }
    public Optional<User> getUserByLoginAndPassword(String login, String password){
        return userRepository.findByLoginAndPassword(login, password);
    }
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> userRepository.delete(value));
    }
    public Optional<User> updateWithPutValueUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            newUser.setUserId(userId);
            return Optional.of( userRepository.save(newUser));
        }
        return user;
    }
}
