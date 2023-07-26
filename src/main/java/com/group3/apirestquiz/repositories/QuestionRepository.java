package com.group3.apirestquiz.repositories;

import com.group3.apirestquiz.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByQuizQuizIdAndQuizUserUserId(Long quizId, Long userId);
    Optional<Question> findByQuestionIdAndQuizQuizIdAndQuizUserUserId(Long id, Long quizId, Long userId);
    List<Question> findAllByTextContaining(String keyword);
    List<Question> findAllByQuizQuizId(Long quizId);
    List<Question> findAllByType(String type);
    List<Question> findAllByPoint(int point);
    List<Question> findAllByRankResponse(int numResponse);
    Optional<Question> findByRank(int rank);
}
