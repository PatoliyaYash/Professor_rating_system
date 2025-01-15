package com.example.humber_review_system.dtos;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long professorId;
    private Long userId;
    private String reviewText;
    private int rating;
}