package com.example.auth.service;

import com.example.auth.model.User;

public interface UserService {
    User authenticate(String username, String password);
    void logout();
    User getUserByUsername(String name);
}
