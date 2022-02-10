package com.firstmilestone.first.service;

import com.firstmilestone.first.entities.User;
import com.firstmilestone.first.repository.CrudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrudServiceTest {

    @Mock
    private CrudRepository crudRepository;


    private  CrudService crudService;


    public  User user = new User("Vivek0099", "Vivek", "Patel", "vivek.patel@paytm.com","8484339901","Pune", "Gwalior");

    @BeforeEach
     void setUp() {

        this.crudService = new CrudService(this.crudRepository);
    }

    @BeforeEach
    void initUseCase() {
        List<User> customers = Arrays.asList(
                new User("Vivek0099", "Vivek", "Patel", "vivek.patel@paytm.com","7878787887","Pune", "Gwalior")
        );
        crudRepository.saveAll(customers);
    }

    @Test
    void getAllUser() {
        List<User> users = crudService.getAllUser();
        verify(crudRepository).findAll();
    }

    @Test
    void getSpecificUser() {

        User user = new User(1,"Vivek0099", "Vivek", "Patel", "vivek.patel@paytm.com","8765432178","Pune", "Gwalior");

        String username = "Vivek0099";

        Mockito.when(crudService.getSpecificUser(username)).thenReturn(user);
        verify(crudRepository).findByUserName("Vivek0099");
    }

    @Test
    void addNewUser() {
        User user = new User("Vivek0099", "Vivek", "Patel", "vivek.patel@paytm.com","9800083498","Pune", "Gwalior");

        User user1 = new User(1,"Vivek0099", "Vivek", "Patel", "vivek.patel@paytm.com","9800083498","Pune", "Gwalior");

        // Providing Knowledge
        when(crudRepository.save(user)).thenReturn(user1);

        User savedUser = crudRepository.save(user);
        assertThat(user1.getId()).isEqualTo(1);
    }

    @Test
    void updateUserDetail() {
    }

    @Test
    void deleteUser() {
    }
}