package com.SmartMart.backend.userController;

import com.SmartMart.backend.Payment.model.User;
import com.SmartMart.backend.Payment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public Response register(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            return new Response(false, "Username taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new Response(true, "User registered");
    }

    static class Response {
        public boolean success;
        public String message;

        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
