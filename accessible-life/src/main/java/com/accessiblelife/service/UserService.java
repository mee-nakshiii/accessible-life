package com.accessiblelife.service;

import com.accessiblelife.dao.UserDAO;
import com.accessiblelife.model.User;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Register a new user
     * @param user com.accessiblelife.model.User object with name, email, password, location
     * @return true if registration successful
     */
    public boolean registerUser(User user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
            return false; // basic validation
        }
        // TODO: You can hash the password here before saving
        return userDAO.register(user);
    }

    /**
     * Login a user with email and password
     * @param email com.accessiblelife.model.User email
     * @param password com.accessiblelife.model.User password
     * @return com.accessiblelife.model.User object if login successful, null otherwise
     */
    public User loginUser(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        return userDAO.login(email, password);
    }
}
