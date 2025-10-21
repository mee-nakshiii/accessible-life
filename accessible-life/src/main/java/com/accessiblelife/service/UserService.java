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

    public List<User> getAllUsers() {
        return userRepo.findAllUsers();
    }

    // FIX: New method to bridge the GUI (ManageUsersForm) and the Repository
    public boolean deleteUser(long userId) {
        if (userId <= 0) {
            System.err.println("Invalid User ID for deletion.");
            return false;
        }
        return userRepo.deleteUser(userId);
    }
}