package com.example.patient.service;

import com.example.patient.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User getUserByUsername(String username);
    User createUser(User user);
    User getUserById(Long userId);
    Long getAuthenticatedUserId(Authentication authentication);
    boolean isEmployee(Authentication authentication);
    boolean isEmployeeOrResourceOwner(Authentication authentication, Long userId);
}
