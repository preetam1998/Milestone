package com.firstmilestone.first.repository;

import com.firstmilestone.first.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;



@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CrudRepositoryTest {

    @Autowired
    private CrudRepository crudRepository;

    public static  Logger logger = LogManager.getLogger(CrudRepositoryTest.class);

   @Test
   @Order(1)
    public void saveUser() throws IOException {

        User user = User.builder().userName("sharukh9999").firstName("Sharukh").lastName("Patel")
                         .email("sharukh@test.com").mobile("7878343456").currentAddress("patna").permanentAddress("bihar")
                        .build();


        crudRepository.save(user);
        logger.info("User Saved :: " + user.toString());
        Assertions.assertThat(user.getId()).isNotEqualTo(0);
   }


   @Test
   @Order(2)
   public void fetchUser()
   {
       User user = crudRepository.findByUserName("sharukh9999");
       logger.info("User fetched :: " + user);
       Assertions.assertThat(user).isNotEqualTo(null);
   }

   @Test
   @Order(3)
   public void updateUser()
   {
       User user = crudRepository.findByUserName("sharukh9999");
       logger.info("User Before Update :: " + user);
       user.setEmail("srk@test.com");
       crudRepository.save(user);

       User user1 = crudRepository.findByUserName("sharukh9999");
       logger.info("User After Update :: " + user1);
       Assertions.assertThat(user1.getEmail()).isEqualTo("srk@test.com");

   }

   @Test
   @Order(4)
    public void deleteUser()
    {
        User user = crudRepository.findByUserName("sharukh9999");
        logger.info("User Before Delete :: " + user);
        Assertions.assertThat(user).isNotEqualTo(null);

        crudRepository.deleteById(user.getId());

        User user1 = crudRepository.findByUserName("sharukh9999");
        logger.info("User After Delete :: " + user1);
        Assertions.assertThat(user1).isEqualTo(null);

    }
}