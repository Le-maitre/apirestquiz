package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.models.Result;
import com.group3.apirestquiz.repositories.QuizRepository;
import com.group3.apirestquiz.repositories.ResultRepository;
import com.group3.apirestquiz.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ResultRepository resultRepository;

    // Definission des différent méthode pour le service quiz
    public List<Quiz> getQuizzes(){
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long quizId){
        return quizRepository.findById(quizId);
    }

    public List<Quiz> getQuizzesByTitle(String title){
        return quizRepository.findAllByTitleContaining(title);
    }

    public List<Quiz> getQuizzesByDomain(String domain){
        return quizRepository.findAllByDomain(domain);
    }

    public List<Quiz> getQuizzesByNbMaxQuestion(int nbMaxQuestion){
        return quizRepository.findAllByNbMaxQuestion(nbMaxQuestion);
    }

    public List<Quiz> getQuizzesByVisibility(String visibility){
        return quizRepository.findAllByVisibility(visibility);
    }

    public List<Quiz> getQuizzesByCreationDate(Date creationDate){
        return quizRepository.findAllByCreationDate(creationDate);
    }

    public List<Quiz> getQuizzesByUserId(Long userId){
        return quizRepository.findAllByUserUserId(userId);
    }

    public Optional<Quiz> getQuizByUserUserIdAndQuizId(Long userId, Long quizId){
        Optional<Quiz> quiz = quizRepository.findAllByUserUserId(userId).stream().filter(q -> q.getQuizId().equals(quizId)).findFirst();
        return quiz;
    }

    public Quiz addQuiz(Quiz quiz){
        return quizRepository.save(quiz);
    }
    public void deleteQuiz(Long userId, Long quizId) {
        Optional<Quiz> quiz = quizRepository.findByUserUserIdAndQuizId(userId, quizId);
        quiz.ifPresent(value -> quizRepository.delete(value));
    }
    public Optional<Quiz> updateWithPutValueQuiz(Long userId, Long quizId, Quiz newQuiz) {
        Optional<Quiz> quizOptional = quizRepository.findByUserUserIdAndQuizId(userId, quizId);
        if (quizOptional.isPresent()) {
            Quiz existingQuiz = quizOptional.get();

            // Mise à jour des champs du quiz existant avec les nouvelles valeurs
            existingQuiz.setTitle(newQuiz.getTitle());
            existingQuiz.setNbMaxQuestion(newQuiz.getNbMaxQuestion());
            existingQuiz.setVisibility(newQuiz.getVisibility());
            existingQuiz.setDescription(newQuiz.getDescription());
            existingQuiz.setDomain(newQuiz.getDomain());

            // Enregistrez les modifications apportées au quiz existant dans la base de données
            Quiz updatedQuiz = quizRepository.save(existingQuiz);
            return Optional.of(updatedQuiz);
        }
        return Optional.empty();
    }
    public ResponseEntity<Optional<Quiz>> updateWithPathValueQuiz(Long userId, Long quizId, Map<String, Object> updateQuiz) {
        Optional<Quiz> quiz = quizRepository.findByUserUserIdAndQuizId(userId, quizId);
        if(quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Mise à jour des données de l'utilisateur
        if(updateQuiz.containsKey("title")){
            quiz.get().setTitle((String) updateQuiz.get("title"));
        }
        if(updateQuiz.containsKey("nbMaxQuestion")){
            quiz.get().setNbMaxQuestion((int) updateQuiz.get("nbMaxQuestion"));
        }
        if(updateQuiz.containsKey("visibility")){
            quiz.get().setVisibility((String) updateQuiz.get("visibility"));
        }
        if(updateQuiz.containsKey("description")){
            quiz.get().setDescription((String) updateQuiz.get("description"));
        }
        if(updateQuiz.containsKey("creationDate")){
            quiz.get().setCreationDate((LocalDate) updateQuiz.get("creationDate"));
        }
        if(updateQuiz.containsKey("domain")){
            quiz.get().setDomain((String) updateQuiz.get("domain"));
        }

        quizRepository.save(quiz.get());
        return ResponseEntity.ok(quiz);
    }

    public List<Quiz> getQuizzesPlayedByUser(Long userId) {
        // Cette méthode permet de récupérer les quiz joués par un utilisateur

        return resultRepository.findAllByUserUserId(userId).stream()
                .map(Result::getQuiz) // Récupère les quiz à partir des résultats
                .distinct() // Élimine les doublons de quiz
                .collect(Collectors.toList());
    }

    public List<Quiz> getQuizzesPlayedByUserAndDomain(Long userId, String domain) {
        // Cette méthode permet de récupérer les quiz joués par un utilisateur dans un domaine précis
        List<Quiz> quizList = getQuizzesPlayedByUser(userId);
        return quizList.stream()
                .filter(quiz -> quiz.getDomain().equals(domain))
                .collect(Collectors.toList());
    }
}
