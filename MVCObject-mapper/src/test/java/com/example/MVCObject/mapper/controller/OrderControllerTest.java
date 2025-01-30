package com.example.MVCObject.mapper.controller;

import com.example.MVCObject.mapper.entity.Order;
import com.example.MVCObject.mapper.handler.GlobalExceptionHandler;
import com.example.MVCObject.mapper.service.OrderService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class OrderControllerTest {

    @Mock
    private OrderService orderService;


    private OrderController orderController;

    private MockMvc mockMvc;


    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        orderController = new OrderController(orderService, objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

    }

    @Test
    void getAllOrders() throws Exception {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));
        String order1Json = objectMapper.writeValueAsString(order1);
        String order2Json = objectMapper.writeValueAsString(order2);

        List<String> expected = Arrays.asList(order1Json, order2Json);

        List<?> allOrders = orderController.getAllOrders();

        assertEquals(expected, allOrders);

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void getOrderById_Found() throws Exception {
        Order order = new Order();
        when(orderService.getOrderById(1L)).thenReturn(order);

        String orderJson = objectMapper.writeValueAsString(order);

        ResponseEntity<?> response = orderController.getOrderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderJson, response.getBody());

        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void getOrderById_NotFound() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/orders/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).getOrderById(1L);
    }


    @Test
    void createOrder_Success() throws Exception {
        Order order = new Order();
        order.setOrderId(1L);
        order.setShippingAddress("123 Main St");

        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        String orderJson = objectMapper.writeValueAsString(order);

        ResponseEntity<?> response = orderController.createOrder(orderJson);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderJson, response.getBody());

        verify(orderService, times(1)).createOrder(any(Order.class));
    }

    @Test
    public void testCreateOrder_InvalidJson() {
        String invalidOrderJson = "invalid json";

        ResponseEntity<?> response = orderController.createOrder(invalidOrderJson);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Введены неправильные данные", response.getBody());
    }
}
