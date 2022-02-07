package com.example.second.service;

import com.example.second.dao.WalletRepo;
import com.example.second.models.UserWallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepo walletRepo;

    @Autowired
    @InjectMocks
    private WalletService walletService;

    private List<UserWallet> userWalletList;
    private UserWallet userWallet1;
    private UserWallet userWallet2;

    @BeforeEach
    public void setUp() {
        userWalletList = new ArrayList<>();
        userWallet1 = new UserWallet("1234567890", 12000,true);
        userWallet2 = new UserWallet("0987654321", 34000,true);
        userWalletList.add(userWallet1);
        userWalletList.add(userWallet2);
    }
    @AfterEach
    public void tearDown() {
        userWallet1 = userWallet2 = null;
        userWalletList = null;
    }


    @Test
    void getWallet() throws Exception {

        Mockito.when(walletRepo.findByMobileNumber("1234567890")).thenReturn(userWallet1);

        assertThat(walletService.getWallet(userWallet1.getMobileNumber())).isEqualTo(userWallet1);
    }

    @Test
    void createWallet() {
    }

    @Test
    void addMoneyToWallet() {
    }
}