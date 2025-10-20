package com.accessiblelife.service;

import com.accessiblelife.model.User;
import com.accessiblelife.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepo = new UserRepository();

    public User login(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password);
    }

    public boolean registerUser(User user) {
        return userRepo.saveUser(user);
    }

    // NEW METHOD: Fetch all users for Admin
    public List<User> getAllUsers() {
        return userRepo.findAllUsers();
    }
}