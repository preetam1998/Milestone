package com.example.second.controller;

import com.example.second.models.User;
import com.example.second.models.UserSideResponse;
import com.example.second.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan("CustomUserDetailsService.class")
@AutoConfigureMockMvc
@WebMvcTest(UserControllerTest.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;



    @Test
    void getAllUser() {
    }

    @Test
    void getUser() throws Exception {



    }

    @Test
    void createUser() throws Exception {



    }

    @Test
    void updateUser() {

    }

    @Test
    void deleteUser() {
    }
}