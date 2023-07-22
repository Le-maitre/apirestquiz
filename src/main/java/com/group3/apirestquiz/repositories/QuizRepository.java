package com.group3.apirestquiz.repositories;

import com.group3.apirestquiz.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAllByDomain(String domain);
    List<Quiz> findAllByTitle(String title);
    List<Quiz> findAllByNbMaxQuestion(int nbMaxQuestion);
    List<Quiz> findAllByVisibility(String visibility);
    List<Quiz> findAllByCreationDate(Date creationDate);
    List<Quiz> findAllByUserUserId(Long userId);
}
