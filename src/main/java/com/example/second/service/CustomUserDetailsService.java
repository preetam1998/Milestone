package com.example.second.service;

import com.example.second.dao.UserRepo;
import com.example.second.models.MyUserDetails;
import com.example.second.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUserName(username);
        if(user != null)
        {
            UserDetails userDetails = new MyUserDetails(user);
            System.out.println(userDetails.toString());
            return userDetails;
        }
        else
        {
            throw new UsernameNotFoundException("User not found !");
        }
    }
}
