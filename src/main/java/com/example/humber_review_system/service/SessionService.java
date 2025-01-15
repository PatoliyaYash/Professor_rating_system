package com.example.humber_review_system.service;

import com.example.humber_review_system.entity.Session;
import com.example.humber_review_system.entity.User;
import com.example.humber_review_system.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;

    public void generateNewSession(User user, String refreshToken, String accessToken) {
        List<Session> userSessions = sessionRepository.findByUser(user);
        if (userSessions.size() == SESSION_LIMIT) {
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
        sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken);
        if (session == null) {
            throw new SessionAuthenticationException("Session not found for refreshToken: " + refreshToken);
        }
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    public void updateSession(String refreshToken, String accessToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken);
        session.setAccessToken(accessToken);
        sessionRepository.save(session);
    }

    public String invalidateSession(String accessToken) {
        Session session = sessionRepository.findByAccessToken(accessToken);
        if (session != null) {
            sessionRepository.delete(session);
            return "Session invalidated successfully";
        }
        return "Session not found for accessToken: " + accessToken;
    }
}
