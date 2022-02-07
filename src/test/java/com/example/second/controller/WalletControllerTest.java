package com.example.second.controller;

import com.example.second.dto.JwtRequest;
import com.example.second.service.WalletService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @Autowired
    ObjectMapper objectMapper;


    public String getToken() throws Exception {
        String username = "aman0008";
        String password = "Sen@123";
        JwtRequest user = new JwtRequest(username, password);

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
    void getWallet() throws Exception {

        // Get Token
        String token=getToken();

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.get("/wallet/fetch-wallet/8888888888")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        assertNotNull(result2);
    }

    @Test
    void createWallet() throws Exception {

        // Get Token
        String token=getToken();

        // Fetch Data From Json File
        String path = "src/test/jsonFile/Create_Wallet.json";
        String jsonBody = new String(Files.readAllBytes(Paths.get(path)));

        MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallet/create-wallet")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();
    }

    @Test
    void addAmtToWallet() throws Exception {

        // Get Token
        String token=getToken();

        // Fetch Data From Json File
        String path = "src/test/jsonFile/Create_Wallet.json";
        String jsonBody = new String(Files.readAllBytes(Paths.get(path)));


        // Updating The Amount in Wallet
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/wallet/add-balance/8888888888")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
    }
}