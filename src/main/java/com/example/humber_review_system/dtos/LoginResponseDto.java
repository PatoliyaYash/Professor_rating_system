package com.example.humber_review_system.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class LoginResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;
}
