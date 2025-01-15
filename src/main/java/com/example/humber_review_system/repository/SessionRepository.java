package com.example.humber_review_system.repository;

import com.example.humber_review_system.entity.Session;
import com.example.humber_review_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByUser(User user);

    Session findByRefreshToken(String refreshToken);

    void delete(Session leastRecentlyUsedSession);

    Session findByUserId(Long userId);

    Session findByAccessToken(String accessToken);
}
