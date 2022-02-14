package com.example.second.service;

import com.example.second.dao.UserRepo;
import com.example.second.models.MyUserDetails;
import com.example.second.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    // Get Logger for the User
    private static Logger logger = LogManager.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("CustomUserDetailsService : Verifying User : " + username);
        logger.warn("CustomUserDetailsService : Searching user in Database");

        User user = userRepo.findByUserName(username);
        if(user != null)
        {
            logger.debug("CustomUserDetailsService : User is present in Database");
            UserDetails userDetails = new MyUserDetails(user);
            System.out.println(userDetails.toString());
            return userDetails;
        }
        else
        {
            logger.error("CustomUserDetailsService : User not found");
            throw new UsernameNotFoundException("User not found !");
        }
    }
}
