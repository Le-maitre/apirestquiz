package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.Question;
import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.models.Result;
import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.repositories.QuestionRepository;
import com.group3.apirestquiz.repositories.QuizRepository;
import com.group3.apirestquiz.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ResultService resultService;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private QuizService quizService;


    public List<Question> getQuestions(){
        return questionRepository.findAll();
    }
    public Question addQuestion(Question question, Long userId, Long quizId){
        Optional<Quiz> quiz = quizRepository.findByUserUserIdAndQuizId(userId, quizId);
        question.setQuiz(quiz.get());
        return questionRepository.save(question);
    }
    public Optional<Question> getQuestionById(Long questionId){
        return questionRepository.findById(questionId);
    }
    public List<Question> getQuestionsByPoint(int point){
        return questionRepository.findAllByPoint(point);
    }
    public List<Question> getQuestionsByText(String text){
        return questionRepository.findAllByTextContaining(text);
    }
    public List<Question> getQuestionsByType(String type){
        return questionRepository.findAllByType(type);
    }
    public List<Question> getQuestionsByNumResponse(int numResponse){
        return questionRepository.findAllByRankResponse(numResponse);
    }
    public List<Question> getQuestionsByQuizIdAndUserId(Long quizId, Long userId){
        return questionRepository.findAllByQuizQuizIdAndQuizUserUserId(quizId, userId);
    }
    public Optional<Question> getQuestionByQuizIdAndUserId(Long quizId, Long userId, Long questionId){
        Optional<Question> question = getQuestionsByQuizIdAndUserId(quizId, userId).stream().filter(q-> q.getQuestionId().equals(questionId)).findFirst();
        return question;
    }
    public void deleteQuestion(Long userId, Long quizId, Long questionId) {
        Optional<Question> question = getQuestionByQuizIdAndUserId(quizId, userId, questionId);
        question.ifPresent(value-> questionRepository.delete(value));
    }
    public Optional<Question> getQuestionByRank(int rank) {
        return questionRepository.findByRank(rank);
    }
    public Optional<Question> updateWithPutValueQuestion(Long userId, Long quizId, Long questionId, Question newQuestion) {
        Optional<Question> questionOptional = getQuestionByQuizIdAndUserId(quizId, userId, questionId);
        if(questionOptional.isPresent()) {
            Question existingQuestion = questionOptional.get();
            System.out.println(existingQuestion.getText());

            // Mise à jour des données
            existingQuestion.setText(newQuestion.getText());
            existingQuestion.setType(newQuestion.getType());
            existingQuestion.setPoint(newQuestion.getPoint());
            existingQuestion.setRank(newQuestion.getRank());
            existingQuestion.setRankResponse(newQuestion.getRankResponse());

            Question updateQuestion = questionRepository.save(existingQuestion);
            return Optional.of(updateQuestion);
        }
        return Optional.empty();
    }

    public List<Question> getQuestionsByUserIdAndQuizId(Long userId, Long quizId){
        return questionRepository.findAllByQuizQuizIdAndQuizUserUserId(quizId, userId);
    }

    public Optional<Question> getNextQuestion(Long userId, Long quizId) {
        Optional<Result> result = resultService.getResultByUserIdAndQuizIdAndStateFalse(userId, quizId);
        if (result.isEmpty()){
            Optional<User> user = userService.getUserById(userId);
            Optional<Quiz> quiz = quizService.getQuizById(quizId);
            if (user.isPresent() && quiz.isPresent()){
                Result newResult = new Result();
                newResult.setUser(user.get());
                newResult.setQuiz(quiz.get());;
                result = Optional.of(resultRepository.save(newResult));
            }
            else{
                return Optional.empty();
            }
        }

        int questionAnsweredSize = result.get().getQuestions().size(); // On recupère le nombre de question repondue
        int nbMaxQuestion = result.get().getQuiz().getNbMaxQuestion(); // On récupère le nombre maximal de question
        int rank = 0; // Le rank de la question à retourner
        if(questionAnsweredSize < nbMaxQuestion){
            rank = ++questionAnsweredSize;
        }
        else {
            result.get().setState(true);
            resultRepository.save(result.get());
        }
        return getQuestionByRank(rank);
    }

    public ResponseEntity<Optional<Question>> updateWithPathValueQuiz(Long userId, Long quizId, Long questionId, Map<String, Object> updateQuestion) {
        Optional<Question> question = questionRepository.findByQuestionIdAndQuizQuizIdAndQuizUserUserId(questionId, quizId, userId);
        if(question.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Mise à jour des données de l'utilisateur
        if(updateQuestion.containsKey("text")){
            question.get().setText((String) updateQuestion.get("text"));
        }
        if(updateQuestion.containsKey("type")){
            question.get().setType((String) updateQuestion.get("type"));
        }
        if(updateQuestion.containsKey("point")){
            question.get().setPoint((int) updateQuestion.get("point"));
        }
        if(updateQuestion.containsKey("rank")){
            question.get().setRank((int) updateQuestion.get("rank"));
        }
        if(updateQuestion.containsKey("rankResponse")){
            question.get().setRankResponse((int) updateQuestion.get("rankResponse"));
        }

        questionRepository.save(question.get());
        return ResponseEntity.ok(question);
    }

}
