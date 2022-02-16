package com.example.second.controller;

import com.example.second.dto.JwtRequest;
import com.example.second.dto.Response;
import com.example.second.token.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JwtController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    // Get Logger for the Class
    private static Logger logger = LogManager.getLogger(JwtController.class);

    @PostMapping("/authenticate")
    public @ResponseBody String createAuthenticationToken(@RequestBody JwtRequest requestDto)
    {

        logger.info("Authorization Request Received From Username : " + requestDto.getUserName() + ", Password : " + requestDto.getPassword());


        // Create Response
        Response response = new Response();

        try{
            logger.warn("Authenticating user !!!!!!!");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUserName(), requestDto.getPassword())


            );
        }catch(BadCredentialsException ex){

            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Invalid username or password");
            response.setData("No Token Generated");
            logger.error("Invalid User");
            return response.getData();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUserName());
        String jwtToken = jwtUtil.generateToken(userDetails);

        logger.debug("Token : " + jwtToken);

        // On EveryThing Fine
        response.setData("Token :"  + jwtToken);
        response.setMessage("Token Generated");
        response.setStatus(HttpStatus.CREATED);
        return response.getData();
    }
}