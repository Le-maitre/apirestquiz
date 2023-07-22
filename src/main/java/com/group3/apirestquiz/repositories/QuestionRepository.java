package com.group3.apirestquiz.repositories;

import com.group3.apirestquiz.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByQuizQuizIdAndQuizUserUserId(Long quizId, Long userId);
    List<Question> findAllByTextContaining(String keyword);
    List<Question> findAllByType(String type);
    List<Question> findAllByPoint(int point);
    List<Question> findAllByNumResponse(int numResponse);
    List<Question> findAllByQuizUserUserIdAndQuizQuizId(Long userId, Long quizId);
}
