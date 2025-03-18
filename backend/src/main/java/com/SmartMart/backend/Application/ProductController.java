package com.SmartMart.backend.Application;


import com.SmartMart.backend.Domain.Entity.Product;
import com.SmartMart.backend.External.Repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductController {

    private ProductRepository productRepository;

    //fetch all products
    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    //fetch a product by Id
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable String id){
        return productRepository.findById(id);
    }

    //add a new product
    @PostMapping
    public Product addProduct(@RequestBody Product product){
        return productRepository.save(product);
    }

    //update product details
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product updatedProduct){
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setCategory(updatedProduct.getCategory());
            product.setSubcategory(updatedProduct.getSubcategory());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setDescription(updatedProduct.getDescription());
            product.setAvailability(updatedProduct.getAvailability());

            return productRepository.save(product);
        }).orElse(null);
            }


     //Delete a product
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id){
        productRepository.deleteById(id);
    }
}



