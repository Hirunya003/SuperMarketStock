//package com.SmartMart.backend.External.Repositories;
//
//import com.SmartMart.backend.CustomerOrder.Model.Product;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface ProductRepository extends MongoRepository<Product, String> {
//
//}
//
package com.SmartMart.backend.External.Repositories;

import com.SmartMart.backend.CustomerOrder.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
