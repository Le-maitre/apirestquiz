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
        result.ifPresent(value -> value.setState(true));
        resultRepository.save(result.get());
        return result;
    }
    public Result respondQuestion(Long userId, Long quizId, int answer) {
        Optional<Question> question = questionService.getNextQuestion(userId, quizId);
        Optional<Result> result = getResultByUserIdAndQuizIdAndStateFalse(userId, quizId);
        if (question.isPresent() && result.isPresent()){
            if (question.get().getNumResponse() == answer){
                result.get().setScore(result.get().getScore()+question.get().getPoint());
            }
            result.get().getQuestions().add(question.get());
            resultRepository.save(result.get());
            System.out.println(result.get().getQuestions().size());
            return result.get();
        }
        return null;
    }



    public Map<Integer, Map<String, String>> getMaxScoreResultsByUserAndQuiz(Long quizId) {
        List<Result> maxScoreResults = resultRepository.findMaxScoreResultsByUserAndQuiz(quizId);

        // Traiter les résultats pour les formater sous forme de JSON
        Map<Integer, Map<String, String>> formattedResults = new HashMap<>();
        int rank = 1;
        for (Result result : maxScoreResults) {
            Map<String, String> userData = new HashMap<>();
            userData.put("score", String.valueOf(result.getScore()));
            userData.put("name", result.getUser().getFirstName());
            formattedResults.put(rank, userData);
            rank++;
        }

        return formattedResults;
    }
}
