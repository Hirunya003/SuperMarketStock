//package com.SmartMart.backend.Config;
//import com.mongodb.client.MongoClients;
//import io.github.cdimascio.dotenv.Dotenv;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//
//@Configuration
//public class MongoConfig {
//    @Bean
//    public String mongoUri(){
//        Dotenv dotenv =Dotenv.load();
//        String user =dotenv.get("MONGO_USER");
//        String password =dotenv.get("MONGO_PASSWORD");
//        String cluster =dotenv.get("MONGO_CLUSTER");
//        String database =dotenv.get("MONGO_DATABASE");
//        String uri= "mongodb+srv://" +user+":"+password+"@"+cluster+"/"+database+"?retryWrites=true&w=majority";
//
//        return new MongoTemplate(MongoClients.create(uri),database).toString();
//    }
//
//}
package com.SmartMart.backend.Config;

import com.mongodb.client.MongoClients;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate() {
        Dotenv dotenv = Dotenv.load();
        String user = dotenv.get("MONGO_USER");
        String password = dotenv.get("MONGO_PASSWORD");
        String cluster = dotenv.get("MONGO_CLUSTER");
        String database = dotenv.get("MONGO_DATABASE");

        String uri = "mongodb+srv://" + user + ":" + password + "@" + cluster + "/" + database + "?retryWrites=true&w=majority";
        return new MongoTemplate(MongoClients.create(uri), database);
    }
}
