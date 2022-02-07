package com.example.second.service;

import com.example.second.dao.TransactionRepo;
import com.example.second.dto.TransactionRequestDto;
import com.example.second.enums.TransactionStatus;
import com.example.second.models.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class TransactionServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @InjectMocks
    private TransactionService transactionService;

    // Request Dto for transaction
    TransactionRequestDto transactionRequestDto1 = new TransactionRequestDto("111111111111", "4444444444", 200000.0);
    TransactionRequestDto transactionRequestDto2 = new TransactionRequestDto("4444444444", "6666666666", 60000.0);
    TransactionRequestDto transactionRequestDto3 = new TransactionRequestDto("9685532139", "4444444444", 550000.0);

    // Response Dto for transaction
    Transaction transaction1 = new Transaction(1, 200000.0, TransactionStatus.SUCCESS,    "111111111111", "4444444444" );

//
//    @BeforeEach
//    public void setUp() {
//        productList = new ArrayList<>();
//        product1 = new Product(1, "bread",20);
//        product2 = new Product(2, "jam",200);
//        productList.add(product1);
//        productList.add(product2);
//    }
//    @AfterEach
//    public void tearDown() {
//        product1 = product2 = null;
//        productList = null;
//    }

    @Test
    void sendMoney() {

        transactionRepo.save(transaction1);

    }

    @Test
    void getTransactionHistory() {
    }
}