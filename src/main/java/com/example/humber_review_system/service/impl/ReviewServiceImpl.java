package com.example.humber_review_system.service.impl;

import com.example.humber_review_system.dtos.ReviewDTO;
import com.example.humber_review_system.entity.Review;
import com.example.humber_review_system.exceptions.ResourceNotFoundException;
import com.example.humber_review_system.repository.ProfessorRepository;
import com.example.humber_review_system.repository.ReviewRepository;
import com.example.humber_review_system.repository.UserRepository;
import com.example.humber_review_system.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<ReviewDTO> getReviewsForProfessor(Long professorId) {
        if(!professorRepository.existsById(professorId)){
            throw new ResourceNotFoundException("Professor not found");
        }

        List<Review> reviews = reviewRepository.findByProfessorId(professorId);
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No reviews found for professor with id " + professorId);
        }
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO createReview(Long professorId, ReviewDTO reviewDTO) {
        if (reviewDTO.getReviewText() == null || reviewDTO.getReviewText().isEmpty()) {
            throw new ResourceNotFoundException("Review text cannot be null or empty");
        }

        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        Review review = modelMapper.map(reviewDTO, Review.class);

        review.setProfessor(professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found")));

        review.setUser(userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));

        review = reviewRepository.save(review);

        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getReviewsByUser(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        List<Review> reviews = reviewRepository.findByUserId(userId);
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No reviews found for user with id " + userId);
        }
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id " + reviewId));
        if (!review.getUser().getEmail().equals(username)) {
            throw new ResourceNotFoundException("You are not the owner of this review");
        }
        reviewRepository.deleteById(reviewId);
    }



}