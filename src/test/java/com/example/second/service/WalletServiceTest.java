package com.example.second.service;

import com.example.second.dao.WalletRepo;
import com.example.second.dto.WalletRequestDto;
import com.example.second.models.UserWallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
class WalletServiceTest {

    @MockBean
    private WalletRepo walletRepo;

    @Autowired
    private WalletService walletService;

    List<UserWallet> userWalletList;
    UserWallet userWallet1;
    UserWallet userWallet2;

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

        when(walletRepo.findByMobileNumber("1234567890")).thenReturn(userWallet1);
        assertThat(walletService.getWallet(userWallet1.getMobileNumber())).isEqualTo(userWallet1);

    }


    @Test
    void createWallet() throws Exception {

        WalletRequestDto req = new WalletRequestDto();
        req.setMobileNumber("9999999999");
        req.setAmount(23000.90);

        UserWallet res = new UserWallet();
        res.setMobileNumber("9999999999");
        res.setAmount(23000.90);
        res.setActive(true);

        when(walletRepo.save(ArgumentMatchers.any(UserWallet.class))).thenReturn(res);

        UserWallet created = walletService.createWallet(req);
        assertThat(created.getMobileNumber()).isSameAs(res.getMobileNumber());

    }
}