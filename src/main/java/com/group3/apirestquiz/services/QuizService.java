package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.repositories.QuizRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    // Definission des différent méthode pour le service quiz
    public List<Quiz> getQuizzes(){
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long quizId){
        return quizRepository.findById(quizId);
    }

    public List<Quiz> getQuizzesByTitle(String title){
        return quizRepository.findByTitleContaining(title);
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
        Optional<Quiz> quiz = quizRepository.findByUserUserIdAndQuizId(userId, quizId);
        if(quiz.isPresent()){
            newQuiz.getUser().setUserId(userId);
            return Optional.of( quizRepository.save(newQuiz));
        }
        return quiz;
    }
}
