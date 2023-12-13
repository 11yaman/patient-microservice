package com.example.auth.service.impl;

import com.example.auth.repository.AuthRepository;
import com.example.auth.exception.NotFoundException;
import com.example.auth.model.User;
import com.example.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final AuthRepository authRepository;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, AuthRepository authRepository) {
        this.authenticationManager = authenticationManager;
        this.authRepository = authRepository;
    }

    @Override
    public User authenticate(String username, String password) {
        try{
            Authentication authenticationResponse = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(username, password)
                    );
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
            return getUserByUsername(username);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new NotFoundException("User not found: " + username);
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public User getUserByUsername(String name) {
        return authRepository.getUserByUsername(name);
    }
}
