package com.SmartMart.backend.Payment.controller;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://<username>:<password>@cluster.mongodb.net/<database_name>?retryWrites=true&w=majority");

    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "<database_name>");
    }
}
