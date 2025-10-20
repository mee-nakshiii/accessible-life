package com.accessiblelife.service;

import com.accessiblelife.model.User;
import com.accessiblelife.repository.UserRepository;

public class UserService {
    private final UserRepository userRepo = new UserRepository();

    public User login(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password);
    }

    public boolean registerUser(User user) {
        return userRepo.saveUser(user);
    }
}