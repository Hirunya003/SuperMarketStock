package com.store.inventory_management.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.store.inventory_management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MongoConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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
    public CommandLineRunner seedInitialUsers(MongoTemplate mongoTemplate) {
        return args -> {
            // Check if manager exists, if not, create it
            Query managerQuery = new Query(Criteria.where("email").is("manager@gmail.com"));
            if (mongoTemplate.findOne(managerQuery, User.class) == null) {
                User manager = new User();
                manager.setEmail("manager@gmail.com");
                manager.setPassword(passwordEncoder.encode("manager123"));
                manager.setRole("ROLE_MANAGER");
                mongoTemplate.save(manager, "users");
            }

            // Check if admin exists, if not, create it
            Query adminQuery = new Query(Criteria.where("email").is("admin@gmail.com"));
            if (mongoTemplate.findOne(adminQuery, User.class) == null) {
                User admin = new User();
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                mongoTemplate.save(admin, "users");
            }
        };
    }
}