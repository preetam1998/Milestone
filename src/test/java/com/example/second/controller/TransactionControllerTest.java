package com.example.second.controller;

import com.example.second.dto.TransactionRequestDto;
import com.example.second.enums.TransactionStatus;
import com.example.second.models.Transaction;
import com.example.second.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureWebMvc
@AutoConfigureMockMvc
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @MockBean
    TransactionService transactionService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    // Request Dto for transaction
    TransactionRequestDto transactionRequestDto1 = new TransactionRequestDto("111111111111", "4444444444", 200000.0);
    TransactionRequestDto transactionRequestDto2 = new TransactionRequestDto("4444444444", "6666666666", 60000.0);
    TransactionRequestDto transactionRequestDto3 = new TransactionRequestDto("9685532139", "4444444444", 550000.0);

    // Response Dto for transaction
    Transaction transaction1 = new Transaction(1, 200000.0,TransactionStatus.SUCCESS,    "111111111111", "4444444444" );
    Transaction transaction2 = new Transaction(2, 60000.0, TransactionStatus.SUCCESS,  "4444444444", "6666666666");
    Transaction transaction3 = new Transaction(3, 550000.0, TransactionStatus.SUCCESS,  "9685532139", "4444444444");



    @Test
    void sendMoney() throws Exception {

        Mockito.when(transactionService.sendMoney(transactionRequestDto2.getPayerMobile(), transactionRequestDto2.getPayeeMobile(),
                transactionRequestDto2.getAmount())).thenReturn(transaction2);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/transaction/transfer")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
        );


    }

    @Test
    void getTransactionHistory() throws Exception {
        List<Transaction> transactions = new ArrayList<>(Arrays.asList(transaction1,transaction2,transaction3));

        Mockito.when(transactionService.getTransactionHistory("4444444444")).thenReturn(transactions);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/transaction-history/44444444444")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                );

    }

}