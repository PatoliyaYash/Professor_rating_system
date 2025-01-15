package com.example.humber_review_system.repository;

import com.example.humber_review_system.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long id);

    List<Review> findByProfessorId(Long professorId);

    List<Review> findByUserId(Long userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.professor.id = :professorId")
    Double findAverageRatingByProfessorId(Long professorId);
}
