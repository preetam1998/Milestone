package com.example.second.service;

import com.example.second.dao.TransactionRepo;
import com.example.second.dto.TransactionRequestDto;
import com.example.second.enums.TransactionStatus;
import com.example.second.models.Transaction;
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
class TransactionServiceTest {

    @MockBean
    private TransactionRepo transactionRepo;

    @Autowired
    private TransactionService transactionService;

    // Request Dto for transaction
    TransactionRequestDto transactionRequestDto1 = new TransactionRequestDto("111111111111", "4444444444", 200000.0);
    TransactionRequestDto transactionRequestDto2 = new TransactionRequestDto("4444444444", "6666666666", 60000.0);
    TransactionRequestDto transactionRequestDto3 = new TransactionRequestDto("9685532139", "4444444444", 550000.0);

    // Response Dto for transaction
    Transaction transaction1 = new Transaction(1, 200000.0, TransactionStatus.SUCCESS,    "111111111111", "4444444444" );



    @Test
    void sendMoney() {

        // Request Data
        TransactionRequestDto req = new TransactionRequestDto();
        req.setPayerMobile("9685532139");
        req.setPayeeMobile("6666666666");
        req.setAmount(8000.0);

        // response Data
        Transaction res = new Transaction();
        res.setId(1);
        res.setPayer(req.getPayerMobile());
        res.setPayee(req.getPayeeMobile());
        res.setStatus(TransactionStatus.SUCCESS);
        res.setAmount(8000.0);

        when(transactionRepo.save(ArgumentMatchers.any(Transaction.class))).thenReturn(res);

        Transaction created = transactionService.sendMoney(req.getPayerMobile(), req.getPayeeMobile(), req.getAmount());
        assertThat(created.getAmount()).isSameAs(8000.0);
    }

    @Test
    void getTransactionHistory() {
        String mobile = "4444444444";

        List<Transaction> res = new ArrayList<>();
        when(transactionRepo.findByMobileNumber(mobile)).thenReturn(res);

        List<Transaction> result = transactionService.getTransactionHistory(mobile);

        assertThat(res.size()).isEqualTo(result.size() );


    }
}