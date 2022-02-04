package com.example.second.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       // System.out.println(username + "check1");
        if(username.equals("Milestone"))
        {
            //System.out.println(username + "check2");
            return new User("Milestone","1000", new ArrayList<>());
        }else
        {
            throw new UsernameNotFoundException("User not found !");
        }
    }
}
