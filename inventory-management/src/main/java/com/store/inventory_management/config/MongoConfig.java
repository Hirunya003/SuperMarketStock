package com.store.inventory_management.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.store.inventory_management.model.User;
import com.store.inventory_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    private final UserRepository userRepository;

    public MongoConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected String getDatabaseName() {
        return databaseName; // "SmartMart" from application.properties
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri); // Uses the URI from application.properties
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // For encrypting passwords
    }

    // Seed initial data (manager and admin) on application startup
    @Bean
    public void seedInitialUsers() {
        // Check if manager exists, if not, create it
        if (userRepository.findByEmail("manager@gmail.com").isEmpty()) {
            User manager = new User();
            manager.setEmail("manager@gmail.com");
            manager.setPassword(passwordEncoder().encode("manager123")); // Encrypt password
            manager.setRole("MANAGER");
            userRepository.save(manager);
        }

        // Check if admin exists, if not, create it
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {=
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder().encode("admin123")); // Encrypt password
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
    }
}
