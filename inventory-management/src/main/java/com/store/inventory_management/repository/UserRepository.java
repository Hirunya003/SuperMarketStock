package com.store.inventory_management.repository;

import com.store.inventory_management.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Find a user by email (used for login and authentication)
    Optional<User> findByEmail(String email);

    // Find a user by cashierUsername (for cashier-specific queries)
    Optional<User> findByCashierUsername(String cashierUsername);

    // Find all users with a specific role (e.g., all cashiers for admin management)
    List<User> findByRole(String role);

    // Check if a user exists by email (to prevent duplicate registrations)
    boolean existsByEmail(String email);

    // Check if a user exists by cashierUsername (to prevent duplicate usernames)
    boolean existsByCashierUsername(String cashierUsername);

    // Find a user by cashierCode (for admin updates)
    Optional<User> findByCashierCode(String cashierCode);
}
