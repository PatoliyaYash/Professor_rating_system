package com.example.humber_review_system.controller;

import com.example.humber_review_system.dtos.ReviewDTO;
import com.example.humber_review_system.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{professorId}")
    public List<ReviewDTO> getReviewsForProfessor(@PathVariable Long professorId) {
        return reviewService.getReviewsForProfessor(professorId);
    }

    @PostMapping("create/{professorId}")
    public ReviewDTO createReview(@PathVariable Long professorId, @RequestBody ReviewDTO reviewDTO) {
        return reviewService.createReview(professorId, reviewDTO);
    }

    @GetMapping("/user/{userId}")
    public List<ReviewDTO> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId);
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }
}