package com.example.second.dao;

import com.example.second.models.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DataJpaTest
class TransactionRepoTest {

    @Autowired
    private TransactionRepo transactionRepo;

//    @BeforeEach
//    void initUseCase() {
//        List<Transaction> customers = Arrays.asList(
//
//                new Transaction(1, 200000.0, TransactionStatus.SUCCESS,    "111111111111", "444444444444"),
//                new Transaction(2, 6700.0,   TransactionStatus.SUCCESS,    "898989898945", "982394728497"),
//                new Transaction(3, 450000.0, TransactionStatus.SUCCESS,    "982394728497", "111111111111")
//
//        );
//        transactionRepo.saveAll(customers);
//    }

//    @AfterEach
//    public void destroyAll(){
//        transactionRepo.deleteAll();
//    }
//
    @Test
    public void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<Transaction> tutorials = transactionRepo.findAll();
        assertThat(tutorials).isEmpty();
    }

    @Test
    void findByMobileNumber() {
    }
}