package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.Question;
import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.models.Result;
import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.repositories.QuestionRepository;
import com.group3.apirestquiz.repositories.QuizRepository;
import com.group3.apirestquiz.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<Question> getQuestionByPoint(int point){
        return questionRepository.findAllByPoint(point);
    }
    public List<Question> getQuestionByText(String text){
        return questionRepository.findAllByTextContaining(text);
    }
    public List<Question> getQuestionByType(String type){
        return questionRepository.findAllByType(type);
    }
    public List<Question> getQuestionByNumResponse(int numResponse){
        return questionRepository.findAllByNumResponse(numResponse);
    }
    public Optional<Question> getQuestionByRank(int rank) {
        return questionRepository.findByRank(rank);
    }
    public List<Question> getQuestionsByQuizIdAndUserId(Long quizId, Long userId){
        return questionRepository.findAllByQuizQuizIdAndQuizUserUserId(quizId, userId);
    }

    public Optional<Question> getQuestionByQuizIdAndUserId(Long quizId, Long userId, Long questionId){
        Optional<Question> question = getQuestionsByQuizIdAndUserId(quizId, userId).stream().filter(q-> q.getQuestionId().equals(questionId)).findFirst();
        return question;
    }
    public List<Question> getQuestionsByUserIdAndQuizId(Long userId, Long quizId){
        return questionRepository.findAllByQuizUserUserIdAndQuizQuizId(userId, quizId);
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
        return getQuestionByRank(rank);
    }

}
