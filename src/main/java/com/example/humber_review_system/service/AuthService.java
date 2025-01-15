package com.example.humber_review_system.service;

import com.example.humber_review_system.dtos.LoginDto;
import com.example.humber_review_system.dtos.LoginResponseDto;
import com.example.humber_review_system.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        log.info("tokens generated successfully accessToken: {}, refreshToken: {}", accessToken, refreshToken);
        sessionService.generateNewSession(user, refreshToken, accessToken);
        log.info("User {} logged in successfully", user.getEmail());

        return new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        User user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        sessionService.updateSession(refreshToken, accessToken);

        return new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }

    public void logout(String accessToken) {
        sessionService.invalidateSession(accessToken);
    }
}
