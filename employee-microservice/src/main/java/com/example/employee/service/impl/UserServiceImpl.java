package com.example.employee.service.impl;

import com.example.employee.exception.DuplicatedUserInfoException;
import com.example.employee.exception.NotFoundException;
import com.example.employee.model.User;
import com.example.employee.repository.UserRepository;
import com.example.employee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new DuplicatedUserInfoException();

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public Long getAuthenticatedUserId(Authentication authentication) {
        String username = getAuthenticatedUsername(authentication);
        return userRepository.getIdByUsername(username);
    }

    @Override
    public String getAuthenticatedUsername(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaim("preferred_username");
    }

    @Override
    public boolean isEmployee(Authentication authentication) {
        return authentication!=null &&  authentication.getAuthorities().stream()
                .anyMatch(auth -> "EMPLOYEE".equals(auth.getAuthority()));
    }

    @Override
    public boolean isEmployeeOrResourceOwner(Authentication authentication, Long userId) {
        return isEmployee(authentication) ||
                userId.equals(getAuthenticatedUserId(authentication));
    }
}
