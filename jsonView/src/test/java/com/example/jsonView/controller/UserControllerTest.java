package com.example.jsonView.controller;

import com.example.jsonView.entity.User;
import com.example.jsonView.handler.GlobalExceptionHandler;
import com.example.jsonView.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllUsers() throws Exception {

        User user1 = new User(1L, "User One", "user1@example.com");
        User user2 = new User(2L, "User Two", "user2@example.com");
        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"));

        verify(userService, times(1)).findAll();
    }

    @Test
    void getAllUsers_ThrowsException() throws Exception {
        when(userService.findAll()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Database error"));

        verify(userService, times(1)).findAll();
    }


    @Test
    void getUserById_Found() throws Exception {

        User user = new User(1L, "Test User", "user@example.com");
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));

        verify(userService, times(1)).findById(1L);
    }


    @Test
    void getUserById_ThrowsException() throws Exception {

        when(userService.findById(1L)).thenThrow(new RuntimeException("User not found"));


        mockMvc.perform(get("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User not found"));

        verify(userService, times(1)).findById(1L);
    }


    @Test
    void createUser() throws Exception {

        User newUser = new User(null, "New User", "new@example.com");
        User savedUser = new User(1L, "New User", "new@example.com");
        when(userService.save(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.name").value("New User"));


        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void createUser_ThrowsException() throws Exception {

        when(userService.save(any(User.class))).thenThrow(new RuntimeException("Failed to save user"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"some_name\": \"Test User\", \"some_email\": \"test@example.com\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to save user"));

        verify(userService, times(1)).save(any(User.class));
    }


    @Test
    void updateUser_Found() throws Exception {

        User existingUser = new User(1L, "Old User", "old@example.com");
        User updatedUser = new User(1L, "New User", "new@example.com");
        when(userService.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userService.save(any(User.class))).thenReturn(updatedUser);


        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.name").value("New User"));


        verify(userService, times(1)).findById(1L);
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_NotFound() throws Exception {

        when(userService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new User())))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findById(1L);
        verify(userService, never()).save(any(User.class));
    }

    @Test
    void updateUser_ThrowsException() throws Exception {

        when(userService.findById(1L)).thenThrow(new RuntimeException("Failed to update user"));

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test User\", \"email\": \"test@example.com\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to update user"));

        verify(userService, times(1)).findById(1L);
    }


    @Test
    void deleteUser_Found() throws Exception {

        User user = new User(1L, "Test User", "user@example.com");
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).findById(1L);
        verify(userService, times(1)).delete(user);
    }

    @Test
    void deleteUser_NotFound() throws Exception {

        when(userService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findById(1L);
        verify(userService, never()).delete(any(User.class));
    }


    @Test
    void deleteUser_ThrowsException() throws Exception {

        when(userService.findById(1L)).thenThrow(new RuntimeException("Failed to delete user"));

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to delete user"));

        verify(userService, times(1)).findById(1L);
    }

}
