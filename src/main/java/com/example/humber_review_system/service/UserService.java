package com.example.humber_review_system.service;

import com.example.humber_review_system.dtos.SignUpDto;
import com.example.humber_review_system.dtos.UserDto;
import com.example.humber_review_system.entity.User;

public interface UserService {
    User getUserById(Long usedId);
    UserDto signUp(SignUpDto signUpDto);
    User getUsrByEmail(String email);
    User save(User newUser);
    void assignAdminRole(Long userId);
//    boolean isAdmin();
}
