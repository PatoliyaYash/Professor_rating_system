package com.example.humber_review_system.service;

import com.example.humber_review_system.dtos.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviewsForProfessor(Long professorId);
    ReviewDTO createReview(Long professorId, ReviewDTO reviewDTO);

    List<ReviewDTO> getReviewsByUser(Long userId);

    void deleteReview(Long reviewId);
}
