package com.group3.apirestquiz.repositories;

import com.group3.apirestquiz.models.Choise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChoiseRepository extends JpaRepository<Choise, Long> {
    Optional<Choise> findByChoiseIdAndQuestionQuestionIdAndQuestionQuizQuizIdAndQuestionQuizUserUserId(Long choiseId, Long questionId, Long quizId, Long userId);
}
