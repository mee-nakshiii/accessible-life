package com.accessiblelife.controller;

import com.accessiblelife.model.User;
import com.accessiblelife.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService = new UserService();

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }
}

