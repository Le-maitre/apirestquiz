package com.group3.apirestquiz.repositories;

import com.group3.apirestquiz.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findAllByUserUserIdAndQuizQuizId(Long userId, Long quizId);
    List<Result> findAllByQuizQuizId(Long quizId);
    Optional<Result> findByQuizQuizIdAndResultId(Long quizId, Long resultId);
    List<Result> findAllByQuizQuizIdAndScore(Long quizId, int score);
    Optional<Result> findByUserUserIdAndQuizQuizIdAndStateFalse(Long userId, Long quizId);

    // Obtenir la liste des resultats, dont le score est le plus grand, pour chaque utilisateur qui a participer à un quiz donné
    @Query("SELECT r FROM Result r WHERE r.quiz.quizId = :quizId AND r.score = (SELECT MAX(r2.score) FROM Result r2 WHERE r2.user.userId = r.user.userId AND r2.quiz.quizId = :quizId) ORDER BY r.score DESC")
    List<Result> findMaxScoreResultsByUserAndQuiz(@Param("quizId") Long quizId);

}
