package com.example.second.controller;

import com.example.second.models.User;
import com.example.second.models.UserSideResponse;
import com.example.second.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan("CustomUserDetailsService.class")
@AutoConfigureMockMvc
@WebMvcTest(UserControllerTest.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    User u1 = new User(1,"preetam12","preetam","patel", "pre19", "9898989898", "preetam@g.c", "asdjnakca", "bdjhabd",true, "USER");
    User u2 = new User(2,"shiv01","shivam","patel", "pre19", "8898989898", "shiv@g.c", "asdjnakca", "bdjhabd",true, "USER");
    User u3 = new User(3,"nishu12","nishant","patel", "pre19", "9998989898", "nish@g.c", "asdjnakca", "bdjhabd",true, "USER");


    @Test
    void getAllUser() throws Exception {

        List<User> users = Arrays.asList(u1, u2, u3);

        Mockito.when(userService.getAllUser()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getUser() throws Exception {
        User u1 = new User(1,"preetam12","preetam","patel", "pre19", "9898989898", "preetam@g.c", "asdjnakca", "bdjhabd",true, "USER");
        String mobile  = "9898989898";

        Mockito.when(userService.getSpecificUser(mobile)).thenReturn(u1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/9898989898")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createUser() throws Exception {
        UserSideResponse userSideResponse = new UserSideResponse("preetam12", "preetam", "aptel", "pre19", "9898989898", "preetam@g.c", "asdjnakca", "bdjhabd");
        User u1 = new User(1,"preetam12","preetam","patel", "pre19", "9898989898", "preetam@g.c", "asdjnakca", "bdjhabd",true, "USER");

        Mockito.when(userService.addNewUser(userSideResponse)).thenReturn(u1);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("user/addNewUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated()
                );
    }

    @Test
    void updateUser() throws Exception {

        UserSideResponse userSideResponse = new UserSideResponse("preetam1212", "preetam", "aptel", "pre19", "9898989898", "preetam@g.c", "asdjnakca", "bdjhabd");

        User u1 = new User(1,"preetam12","preetam","patel", "pre19", "9898989898", "preetam@g.c", "asdjnakca", "bdjhabd",true, "USER");

        User u2 = new User(1,"preetam1212","preetam","patel", "pre19", "9898989898", "preetam@g.c", "asdjnakca", "bdjhabd",true, "USER");

        Mockito.when(userService.updateUser("preetam12", userSideResponse)).thenReturn(u2);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/preetam12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                );
    }

    @Test
    void deleteUser() throws Exception {

        User u1 = new User(1,"preetam12","preetam","patel", "pre19", "9898989898", "preetam@g.c", "asdjnakca", "bdjhabd",true, "USER");

        Mockito.when(userService.deleteUser(u1.getMobileNumber())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("user/9898989898")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                );

    }
}