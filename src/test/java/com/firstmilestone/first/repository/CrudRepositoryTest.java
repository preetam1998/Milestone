package com.firstmilestone.first.repository;

import com.firstmilestone.first.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Java6Assertions.assertThat;


@SpringBootTest
class CrudRepositoryTest {


    @Autowired
    private CrudRepository crudRepository;


    @BeforeEach
    void initUseCase() {
        List<User> customers = Arrays.asList(
                new User("Vivek0099", "Vivek", "Patel", "vivek.patel@paytm.com","8484339901","Pune", "Gwalior")
        );
        crudRepository.saveAll(customers);
    }


    @Test
    void saveAll_success() {
        List<User> customers = Arrays.asList(
                new User(  "singh9099",   "Sharukh","signh","sharukh@test.com","9993338989","Vijay path Delhi","india gate,Mumbai"),
                new User("roshni0900",  "Roshni","Patel","roshni@test.com","9999944332", "Vijay Nagar", "Rampur"),
                new User("singh9099",   "Sharuk","singh","singh@test.com","8888333444","Vijay path Delhi","india gate,Mumbai")

        );
        Iterable<User> allCustomer = crudRepository.saveAll(customers);

        AtomicInteger validIdFound = new AtomicInteger();
        allCustomer.forEach(customer -> {
            if(customer.getId()>0){
                validIdFound.getAndIncrement();
            }
        });

        assertThat(validIdFound.intValue()).isEqualTo(3);
    }

    @Test
    void findAll_success() {
        List<User> allCustomer = crudRepository.findAll();
        assertThat(allCustomer.size()).isGreaterThanOrEqualTo(1);
    }
}