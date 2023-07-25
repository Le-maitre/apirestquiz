package com.group3.apirestquiz.repositories;

import com.group3.apirestquiz.models.Choise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiseRepository extends JpaRepository<Choise, Long> {
}
