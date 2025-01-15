package com.example.humber_review_system.repository;

import com.example.humber_review_system.entity.User;
import com.example.humber_review_system.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByRole(Role role);
}
