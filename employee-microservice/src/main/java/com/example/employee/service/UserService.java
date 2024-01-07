package com.example.employee.service;

import com.example.employee.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User getUserByUsername(String username);
    User createUser(User user);
    User getUserById(Long userId);
    Long getAuthenticatedUserId(Authentication authentication);
    String getAuthenticatedUsername(Authentication authentication);
    boolean isEmployee(Authentication authentication);
    boolean isEmployeeOrResourceOwner(Authentication authentication, Long userId);
}
