package com.example.humber_review_system.repository;

import com.example.humber_review_system.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    // Custom queries for professor-specific logic
}
