package com.example.second.controller;

import com.example.second.dto.JwtRequest;
import com.example.second.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TransactionControllerTest {

    @Autowired
    TransactionService transactionService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public String doLogin() throws Exception {
        String username = "aman0008";
        JwtRequest user = new JwtRequest(username, "Sen@123");
        String requestJSON = objectMapper.writeValueAsString(user);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        assertNotNull(response);
        System.out.println(response );
        String getResponse = response.toString();
        String token = getResponse.substring(7);
        return token;
    }

    @Test
    void sendMoney() throws Exception {


        // Get Token for Authentication
        String token = doLogin();


        // Fetch Body For Transaction
        String path = "src/test/jsonFile/transaferMoney.json";
        String jsonBody = new String(Files.readAllBytes(Paths.get(path)));

        // Perform
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/transaction/transfer")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getTransactionHistory() throws Exception {

        // Get Token for Authentication
        String token = doLogin();


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/transaction-history/44444444444")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                );

    }

}