package com.example.MVCObject.mapper.controller;

import com.example.MVCObject.mapper.entity.Product;
import com.example.MVCObject.mapper.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    private ObjectMapper objectMapper;

    @GetMapping
    public List<?> getAllProducts() {


        return productService.getAllProducts().stream().map(product -> {
                    try {
                        return objectMapper.writeValueAsString(product);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) throws JsonProcessingException {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(objectMapper.writeValueAsString(product)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody String productJson) {

        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            Product createdProduct = productService.createProduct(product);

            return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.writeValueAsString(createdProduct));

        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Введены неправильные данные", HttpStatus.BAD_REQUEST);
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody String productJson) throws JsonProcessingException {
        Product productDetails;
        try {
            productDetails = objectMapper.readValue(productJson, Product.class);

        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Введены неправильные данные", HttpStatus.BAD_REQUEST);
        }

        Product updatedProduct = productService.updateProduct(id, productDetails);
        return updatedProduct != null ? ResponseEntity.ok(objectMapper.writeValueAsString(updatedProduct)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}