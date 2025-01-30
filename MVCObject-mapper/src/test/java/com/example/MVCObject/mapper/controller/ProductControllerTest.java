package com.example.MVCObject.mapper.controller;

import com.example.MVCObject.mapper.entity.Product;
import com.example.MVCObject.mapper.handler.GlobalExceptionHandler;
import com.example.MVCObject.mapper.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest {

    @Mock
    private ProductService productService;


    private ProductController productController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        productController = new ProductController(productService, objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

    }

    @Test
    void getAllProducts() throws Exception {
        Product product1 = new Product(1L, "Product 1", "Description 1", 100.0, 10);
        Product product2 = new Product(2L, "Product 2", "Description 2", 200.0, 20);
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductById_Found() throws Exception {
        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10);
        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product 1"));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void getProductById_NotFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void createProduct() throws Exception {
        Product newProduct = new Product(null, "New Product", "New Description", 150.0, 15);
        Product savedProduct = new Product(1L, "New Product", "New Description", 150.0, 15);
        when(productService.createProduct(any(Product.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Product"));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    public void testCreateProduct_InvalidInput() throws Exception {
        String invalidJson = "{ \"invalidField\": \"value\" }";

        ResponseEntity<?> response = productController.createProduct(invalidJson);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Введены неправильные данные", response.getBody());

    }

    @Test
    void updateProduct_Found() throws Exception {
        Product updatedProduct = new Product(1L, "Updated Product", "Updated Description", 200.0, 20);
        when(productService.updateProduct(1L, updatedProduct)).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"));

        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void updateProduct_NotFound() throws Exception {

        Product updatedProduct = new Product(1L, "Updated Product", "Updated Description", 200.0, 20);
        String someJson = objectMapper.writeValueAsString(updatedProduct);

        ResponseEntity<?> response = productController.updateProduct(1l, someJson);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }
}
