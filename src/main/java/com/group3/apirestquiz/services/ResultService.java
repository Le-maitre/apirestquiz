package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.Question;
import com.group3.apirestquiz.models.Result;
import com.group3.apirestquiz.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    @Lazy // En utilisant @Lazy, Spring chargera le bean QuestionService uniquement lorsqu'il sera effectivement utilisé, évitant ainsi la dépendance cyclique au moment du démarrage de l'application.
    private QuestionService questionService;

    public List<Result> getResults(){
        return resultRepository.findAll();
    }
    public Result addResult(Result result) {
        return resultRepository.save(result);
    }
    public Optional<Result> getResultById(Long resultId) {
        return resultRepository.findById(resultId);
    }
    public List<Result> getResultsByUserIdAndQuizId(Long userId, Long quizId) {
        return resultRepository.findAllByUserUserIdAndQuizQuizId(userId, quizId);
    }
    public Optional<Result> getResultByUserIdAndQuizId(Long userId, Long quizId, Long resultId) {
        Optional<Result> result = getResultsByUserIdAndQuizId(userId, quizId).stream().filter(r-> r.getResultId().equals(resultId)).findFirst();
        return result;
    }
    public void deleteResult(Long userId, Long quizId, Long resultId) {
        Optional<Result> result = getResultByUserIdAndQuizId(userId, quizId, resultId);
        result.ifPresent(value->resultRepository.delete(result.get()));
    }
    public List<Result> getResultsByQuizId(Long quizId) {
        return resultRepository.findAllByQuizQuizId(quizId);
    }
    public Optional<Result> getResultByQuizId(Long quizId, Long resultId) {
        return resultRepository.findByQuizQuizIdAndResultId(quizId, resultId);
    }
    public List<Result> getResultsByQuizIdAndScore(Long quizId, int score) {
        return resultRepository.findAllByQuizQuizIdAndScore(quizId, score);
    }
    public Optional<Result> getResultByUserIdAndQuizIdAndStateFalse(Long userId, Long quizId) {
        return resultRepository.findByUserUserIdAndQuizQuizIdAndStateFalse(userId, quizId);
    }
    public Optional<Result> endGame(Long userId, Long quizId) {
        Optional<Result> result = resultRepository.findByUserUserIdAndQuizQuizIdAndStateFalse(userId, quizId);
        if(result.isPresent()){
            result.get().setState(true);
        }
        else {
            return Optional.empty();
        }
        resultRepository.save(result.get());
        return result;
    }
    public Result respondQuestion(Long userId, Long quizId, int answer) {
        Optional<Question> question = questionService.getNextQuestion(userId, quizId);
        Optional<Result> result = getResultByUserIdAndQuizIdAndStateFalse(userId, quizId);
        if (question.isPresent() && result.isPresent()){
            // Vérification si la réponse est correcte
            if (question.get().getRankResponse() == answer){
                result.get().setScore(result.get().getScore()+question.get().getPoint());
                result.get().setNbCorrectQuestion(result.get().getNbCorrectQuestion()+1);
            }
            // Si la réponse est incorrecte
            else {
                result.get().setNbIncorrectQuestion(result.get().getNbIncorrectQuestion()+1);
            }
            result.get().getQuestions().add(question.get());

            int questionAnsweredSize = result.get().getQuestions().size(); // On recupère le nombre de question repondue
            int nbQuestion = result.get().getQuiz().getNbQuestion();

            if(questionAnsweredSize == nbQuestion){
                result.get().setState(true);
            }

            resultRepository.save(result.get());
            return result.get();
        }
        return null;
    }

    /*public Result respondQuestion(Long userId, Long quizId, int answer, Question question) {
        //Optional<Question> question = questionService.getNextQuestion(userId, quizId);
        Result existeResult = new Result();
        Optional<Result> result = getResultByUserIdAndQuizIdAndStateFalse(userId, quizId);
        if (result.isPresent()){
            // Vérification si la réponse est correcte
            if (question.getRankResponse() == answer){
                result.get().setScore(result.get().getScore()+question.getPoint());
                result.get().setNbCorrectQuestion(result.get().getNbCorrectQuestion()+1);
            }
            // Si la réponse est incorrecte
            else {
                result.get().setNbIncorrectQuestion(result.get().getNbIncorrectQuestion()+1);
            }
            result.get().getQuestions().add(question);

            int questionAnsweredSize = result.get().getQuestions().size(); // On recupère le nombre de question repondue
            int nbQuestion = result.get().getQuiz().getNbQuestion();

            if(questionAnsweredSize == nbQuestion){
                result.get().setState(true);
            }

            resultRepository.save(result.get());
            existeResult = result.get();
        }
        return existeResult;
    }*/



    /*public Map<Integer, Map<String, Object>> getMaxScoreResultsByUserAndQuiz(Long quizId) {
        List<Result> maxScoreResults = resultRepository.findMaxScoreResultsByUserAndQuiz(quizId);

        // Traiter les résultats pour les formater sous forme de JSON
        Map<Integer, Map<String, Object>> formattedResults = new HashMap<>();
        int rank = 1;
        for (Result result : maxScoreResults) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("rank", rank);
            userData.put("score", String.valueOf(result.getScore()));
            userData.put("user", result.getUser());
            formattedResults.put(rank, userData);
            rank++;
        }
        return formattedResults;
    }*/


    public Map<Integer, Map<String, Object>> getMaxScoreResultsByUserAndQuiz(Long quizId) {
        List<Result> maxScoreResults = resultRepository.findMaxScoreResultsByUserAndQuiz(quizId);

        // Structure de données pour stocker le meilleur score de chaque utilisateur
        Map<Long, Result> bestScores = new HashMap<>();

        int rank = 1;

        for (Result result : maxScoreResults) {
            Long userId = result.getUser().getUserId();

            // Vérifie si l'utilisateur a déjà un meilleur score
            if (!bestScores.containsKey(userId)) {
                bestScores.put(userId, result);
            } else {
                Result existingResult = bestScores.get(userId);

                // Compare le score actuel avec le score stocké
                if (result.getScore() > existingResult.getScore()) {
                    bestScores.put(userId, result); // Remplace le score stocké
                }
            }
        }

        // Formatter les résultats finaux sous forme de JSON
        Map<Integer, Map<String, Object>> formattedResults = new HashMap<>();

        for (Result bestResult : bestScores.values()) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("rank", rank);
            userData.put("score", String.valueOf(bestResult.getScore()));
            userData.put("user", bestResult.getUser());
            formattedResults.put(rank, userData);
            rank++;
        }

        return formattedResults;
    }


    public List<Result> getResultsByUserId(Long userId) {
        return resultRepository.findAllByUserUserId(userId);
    }
}
