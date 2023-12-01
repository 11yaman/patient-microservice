package com.example.auth.controller;

import com.example.auth.dto.request.AuthenticateRequest;
import com.example.auth.dto.response.UserDto;
import com.example.auth.exception.NotFoundException;
import com.example.auth.mapping.StrategyMapper;
import com.example.auth.model.User;
import com.example.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final StrategyMapper<User, UserDto> userMapper;
    @Autowired
    public AuthController(UserService userService, StrategyMapper<User, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserDto> authenticate(@RequestBody(required = false) AuthenticateRequest authenticateRequest,
                                                Authentication authentication) {
        try {
            User user;
            if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser"))
                user = userService.getUserByUsername(authentication.getName());
            else if (authenticateRequest != null)
                user = userService.authenticate(authenticateRequest.email(), authenticateRequest.password());
            else{
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect user ID or password");
             }
            return new ResponseEntity<>(userMapper.map(user), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect user ID or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        userService.logout();
        return new ResponseEntity<>("Logged out", HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            return ResponseEntity.ok(userMapper.map(user));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
    }
}