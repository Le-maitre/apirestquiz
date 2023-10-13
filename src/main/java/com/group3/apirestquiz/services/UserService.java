package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.Result;
import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.repositories.ResultRepository;
import com.group3.apirestquiz.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResultRepository resultRepository;

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
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User existingUser = userOptional.get();

            existingUser.setFirstName(newUser.getFirstName());
            existingUser.setLastName(newUser.getLastName());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setLogin(newUser.getLogin());
            existingUser.setPassword(newUser.getPassword());

            User upadateUser = userRepository.save(existingUser);
            return Optional.of(upadateUser);
        }
        return Optional.empty();
    }
    public ResponseEntity<Optional<User>> updateWithPathValueUser(Long userId, Map<String, String> updateUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Mise à jour des données de l'utilisateur
        if(updateUser.containsKey("firstName")){
            user.get().setFirstName(updateUser.get("firstName"));
        }
        if(updateUser.containsKey("lastName")){
            user.get().setLastName(updateUser.get("lastName"));
        }
        if(updateUser.containsKey("email")){
            user.get().setEmail(updateUser.get("email"));
        }
        if(updateUser.containsKey("login")){
            user.get().setLogin(updateUser.get("login"));
        }
        if(updateUser.containsKey("password")){
            user.get().setPassword(updateUser.get("password"));
        }

        userRepository.save(user.get());
        return ResponseEntity.ok(user);
    }

    public List<User> getUsersPlayedInQuiz(Long quizId) {
        // Cette méthode permet de récupérer les utilisateurs qui ont joué à un quiz donné

        return resultRepository.findAllByQuizQuizId(quizId).stream()
                .map(Result::getUser) // Récupère les users à partir des résultats
                .distinct() // Élimine les doublons de quiz
                .collect(Collectors.toList());
    }

    public List<User> getFollowers(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get().getFollowers();
        }
        return new ArrayList<>();
    }

    public String followAnUser(Long followerId, Long userSecondId) {
        Optional<User> follower = userRepository.findById(followerId);
        Optional<User> userSecond = userRepository.findById(userSecondId);
        if(follower.isPresent() && userSecond.isPresent()){
            if(!userSecond.get().getFollowers().contains(follower.get()) && !followerId.equals(userSecondId)){
                userSecond.get().getFollowers().add(follower.get());
                follower.get().getFollowings().add(userSecond.get());
                userRepository.save(userSecond.get());
                userRepository.save(follower.get());
                return "Abonnement reussi";
            }
            else{
                return "Vous être déjà abonné ou vous ne pouvez pas vous abonné à votre propre compte";
            }
        }
        return "Erreur: Vous ne pouvez pas vous abonnées";
    }

    public List<User> getFollowings(Long userId) {
        // Récupérer la liste des users qu'un user follow
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return userOptional.get().getFollowings();
        }
        return new ArrayList<>();
    }

    public String noFollowAnUser(Long followerId, Long userSecondId) {
        Optional<User> actuallyFollower = userRepository.findById(followerId);
        Optional<User> actuallyFollowing = userRepository.findById(userSecondId);
        if(actuallyFollower.isPresent() && actuallyFollowing.isPresent()){
            if(actuallyFollower.get().getFollowings().contains(actuallyFollowing.get()) && !followerId.equals(userSecondId)){
                actuallyFollower.get().getFollowings().remove(actuallyFollowing.get());
                actuallyFollowing.get().getFollowers().remove(actuallyFollower.get());
                userRepository.save(actuallyFollower.get());
                userRepository.save(actuallyFollowing.get());
                return "Vous vous ête bien désabonné de "+actuallyFollowing.get().getLogin();
            }
            else{
                return "Impossible de se désabonné car l'utilisateur s'exite pas";
            }
        }
        return "Erreur: Impossible de se désabonné car l'un des users est introuvable";
    }
}
