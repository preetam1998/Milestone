package com.firstmilestone.first.service;

import com.firstmilestone.first.entities.User;
import com.firstmilestone.first.repository.CrudRepository;
import com.firstmilestone.first.validation.ValidateMobEml;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CrudServiceTest {

    @Mock
    private CrudRepository crudRepository;

    @InjectMocks
    private  CrudService crudService;

    @Autowired
    public ValidateMobEml validateMobEml;

    public static final Logger logger = LogManager.getLogger(CrudServiceTest.class);

    @BeforeEach
     void setUp() {
        this.crudService = new CrudService(this.crudRepository, this.validateMobEml);

    }

    @Test
    void getSpecificUser() {

        User user = new User(1,"Vivek0099", "Vivek", "Patel", "vivek.patel@test.com","8765432178","Pune", "Gwalior");
        String username = "Vivek0099";

        // Provide knowledge
        when(crudRepository.findByUserName(username)).thenReturn(user);

        //test
        User userResult = crudService.getSpecificUser(username);
        logger.info("user details  :: " + userResult.toString());
        assertThat(userResult).isNotNull();
    }

    @Test
    @Order(1)
    void addNewUser() {
        User user = new User("Vivek0099", "Vivek", "Patel", "vivek.patel@test.com","9800083498","Pune", "Gwalior");
        User user1 = new User(1,"Vivek0099", "Vivek", "Patel", "vivek.patel@test.com","9800083498","Pune", "Gwalior");

        // Providing Knowledge
        when(crudRepository.save(user)).thenReturn(user1);

        //test
        User userResult = crudService.addNewUser(user);
        logger.info("New User Added  : " + userResult);
        assertThat(user1.getId()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void updateUserDetail() {

        User userBeforeUpdate = new User("Vivek0099", "Vivek", "Singh", "vivek.singh@test.com","9800083498","Pune", "Gwalior");
        User userFetched = new User(1,"Vivek0099", "Vivek", "Patel", "vivek.patel@test.com","9800083498","Pune", "Gwalior");
        User userAfterUpdate = new User(1,"Vivek0099", "Vivek", "Singh", "vivek.singh@test.com","9800083498","Pune", "Gwalior");

        // Provide Knowledge
        when(crudRepository.findByUserName(userBeforeUpdate.getUserName())).thenReturn(userFetched);

        // test
        logger.info("User Before Update : " + userBeforeUpdate);
        User userResult = crudService.updateUserDetail(userAfterUpdate, userBeforeUpdate.getUserName());
        logger.info("User After Update : " + userResult);

        assertThat(userResult.getEmail()).isEqualTo("vivek.singh@test.com");
    }

    @Test
    @Order(3)
    void deleteUser() {

        User userFetched = new User(1,"Vivek0099", "Vivek", "Patel", "vivek.patel@test.com","9800083498","Pune", "Gwalior");

        // Provide knowledge
        when(crudRepository.existsByUserName(userFetched.getUserName())).thenReturn(true);

        //test
        Boolean result = crudService.deleteUser(userFetched.getUserName());
        assertThat(result).isTrue();
    }
}