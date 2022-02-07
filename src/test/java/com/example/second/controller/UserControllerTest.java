package com.example.second.controller;

import com.example.second.dto.JwtRequest;
import com.example.second.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserController userController;


    public String getToken() throws Exception {
        String username = "aman0008";
        JwtRequest user = new JwtRequest(username, "Sen@123");
        String requestJSON = objectMapper.writeValueAsString(user);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJSON))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        assertNotNull(response);
        System.out.println(response );
        String getResponse = response.toString();
        String token = getResponse.substring(7);
        return token;
    }




    @Test
    void getUser() throws Exception {

        // Get Token
        String token = getToken();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/9898989898")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void createUser() throws Exception {

        // Get Token
        String token = getToken();

        // Get Body For request
        String path = "src/test/jsonFile/Create_User.json";
        String requestBody =  new String(Files.readAllBytes(Paths.get(path)));


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/addNewUser")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    void updateUser() throws Exception {

        // Get Token
        String token = getToken();

        // Get Body For request
        String path = "src/test/jsonFile/Create_User.json";
        String requestBody =  new String(Files.readAllBytes(Paths.get(path)));


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/user/kapil1111")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void deleteUser() throws Exception {


        // Get Token
        String token = getToken();

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/9999999999")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()
                    );

    }


}