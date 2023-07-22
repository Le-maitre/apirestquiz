package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.Question;
import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.repositories.QuestionRepository;
import com.group3.apirestquiz.repositories.QuizRepository;
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

    public List<Question> getQuestions(){
        return questionRepository.findAll();
    }
    public Question addQuestion(Question question, Long userId, Long quizId){
        Quiz quiz = quizRepository.findByUserUserIdAndQuizId(userId, quizId);
        question.setQuiz(quiz);
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

}
