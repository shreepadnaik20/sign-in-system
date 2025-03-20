package com.example.sign.controller;

import com.example.sign.model.User;
import com.example.sign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthCcontroller {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";  // Returns the registration HTML page
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        if (userService.isUsernameExists(username)) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }
        if (userService.isEmailExists(email)) {
            model.addAttribute("error", "Email already exists!");
            return "register";
        }

        userService.registerUser(username, email, password);
        model.addAttribute("message", "Registration Successful!");
        return "login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // Returns the login HTML page
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            model.addAttribute("username", username);
            model.addAttribute("message", "Login Successful!");
            return "welcome";
        } else {
            model.addAttribute("error", "Invalid Credentials");
            return "login";
        }
    }
}
