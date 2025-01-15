package com.example.humber_review_system.controller;

import com.example.humber_review_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/assign-admin-role/{userId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<String> assignAdminRole(@PathVariable Long userId) {
        userService.assignAdminRole(userId);
        return ResponseEntity.ok("Admin role assigned successfully to user with id " + userId);
    }
}
