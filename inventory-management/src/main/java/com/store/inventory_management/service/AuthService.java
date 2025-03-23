package com.store.inventory_management.service;

import com.store.inventory_management.config.JwtUtil;
import com.store.inventory_management.exception.UnauthorizedException;
import com.store.inventory_management.model.User;
import com.store.inventory_management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String email, String password) {
        logger.info("Login attempt for email: {}", email);
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                logger.warn("User not found with email: {}", email);
                throw new UnauthorizedException("Invalid email or password");
            }

            User user = userOptional.get();
            if (user.getPassword() == null) {
                logger.error("Password is null for user: {}", email);
                throw new IllegalStateException("User password is not set");
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                logger.warn("Password mismatch for email: {}", email);
                throw new UnauthorizedException("Invalid email or password");
            }

            logger.info("User authenticated successfully: {}", email);
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
            );
            String token = jwtUtil.generateToken(userDetails);
            logger.info("JWT token generated for user: {}", email);
            return token;
        } catch (UnauthorizedException e) {
            throw e; // Re-throw to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            logger.error("Unexpected error during login for email: {}", email, e);
            throw new RuntimeException("Login failed due to an unexpected error", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Loading user by email: {}", email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            logger.warn("User not found with email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        User user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}