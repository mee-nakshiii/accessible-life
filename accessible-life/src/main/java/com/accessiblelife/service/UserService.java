package com.accessiblelife.service;

import com.accessiblelife.dao.UserDAO;
import com.accessiblelife.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    // Register a new user
    /*public User registerUser(User user) {
        return userDAO.registerUser(user);
    }*/
    // Register a new user
    public User registerUser(User user) {
        // Check if email already exists
        if (userDAO.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        return userDAO.registerUser(user);
    }


    // Login method
    public User login(String email, String passwordHash) {
        Optional<User> optionalUser = userDAO.findByEmailAndPasswordHash(email, passwordHash);
        return optionalUser.orElse(null);
    }

    // Find user by ID
    public User getUserById(Long id) {
        Optional<User> optionalUser = userDAO.findById(id);
        return optionalUser.orElse(null);
    }
}

  // <-- only closing brace of class
