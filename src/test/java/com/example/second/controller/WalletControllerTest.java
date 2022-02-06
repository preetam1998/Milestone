package com.example.second.controller;

import com.example.second.dto.WalletRequestDto;
import com.example.second.models.UserWallet;
import com.example.second.service.WalletService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;



    private UserWallet userWallet1 ;
    private WalletRequestDto walletRequestDto;
    List<UserWallet> userWallets;
    @BeforeEach
    public void setUp() {
        userWallets = new ArrayList<>();
        userWallet1 = new UserWallet("1212121212", 1234.90,true);
        walletRequestDto = new WalletRequestDto("1212121212", 1234.90);

        userWallets.add(userWallet1);

        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }
    @AfterEach
    public void tearDown() {
        userWallet1  = null;
        userWallets = null;
        walletRequestDto = null;
    }

    @Test
    void getWallet() throws Exception {

        when(walletService.getWallet(userWallet1.getMobileNumber())).thenReturn(userWallet1);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/wallet/fetch-wallet/1212121212")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createWallet() throws Exception {

        when(walletService.createWallet(walletRequestDto)).thenReturn(userWallet1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallet/create-wallet")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void addAmtToWallet() throws Exception {

        when(walletService.addMoneyToWallet(userWallet1.getMobileNumber(), walletRequestDto.getAmount())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/wallet/add-balance/1212121212")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}