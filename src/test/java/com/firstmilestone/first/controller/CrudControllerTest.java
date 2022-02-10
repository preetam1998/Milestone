package com.firstmilestone.first.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstmilestone.first.FirstApplicationTests;
import com.firstmilestone.first.entities.User;
import com.firstmilestone.first.service.CrudService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CrudController.class)

class CrudControllerTest {


    // Take help From This blog
    //https://blog.devgenius.io/spring-boot-deep-dive-on-unit-testing-92bbdf549594
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private CrudService crudService;


    User u1 = new User(1,"preetam1998", "Preetam","Vinayak","preetam@test.com","8989343455","Akash Vihar","DB Mall, DU");
    User u2 = new User(2,  "vatsal1001",  "Vatsal","Rastogi","rastogi@test.com","9090887333", "Vijay Nagar", "Rampur");
    User u3 = new User( 3, "singh9099",   "Sharukh","signh","sharukh@test.com","9993338989","Vijay path Delhi","india gate,Mumbai");
    User u4 = new User("roshni0900",  "Roshni","Patel","roshni@test.com","9999944332", "Vijay Nagar", "Rampur");
    User u5 = new User("singh9099",   "Sharuk","singh","singh@test.com","8888333444","Vijay path Delhi","india gate,Mumbai");



    @Test
    void getAllUser() throws Exception{

        Mockito.when(crudService.getAllUser()).thenReturn(Arrays.asList(u1, u2, u3));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/all")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getSpecificUser() throws Exception {

        Mockito.when(crudService.getSpecificUser(u2.getUserName())).thenReturn(u2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/vatsal1001")
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void addNewUser() throws Exception {

        User user = User.builder()
                .userName("Sweta1999")
                .firstName("Shweta")
                .lastName("punjabi")
                .email("sweta@g.c")
                .mobile("8989898989")
                .currentAddress("SBI Chawk")
                .permanentAddress("Madan Mahal").build();

        User user1 = User.builder()
                .id(1)
                .userName("Sweta1999")
                .firstName("Sweta")
                .lastName("punjabi")
                .email("sweta@g.c")
                .mobile("8989898989")
                .currentAddress("SBI Chawk")
                .permanentAddress("Madan Mahal").build();

        System.out.println(user);
        System.out.println(user);
        Mockito.when(crudService.addNewUser(user)).thenReturn(user1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(user));


        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void updateUser() throws Exception {
        User user = User.builder()
                .id(1)
                .userName("Sweta1999")
                .firstName("shewta")
                .lastName("Punjabi")
                .email("sweta@g.c")
                .mobile("8989898989")
                .currentAddress("SBI Chawk")
                .permanentAddress("Madan Mahal").build();

        crudService.addNewUser(user);
        Mockito.when(crudService.updateUserDetail(user, user.getUserName())).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/user/Sweta1999")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(user));

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {

        crudService.addNewUser(u5);
        Mockito.when(crudService.deleteUser(u5.getUserName())).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/user/singh9099")
        )
                .andDo(print());
    }
}