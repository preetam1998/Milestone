package com.example.second.controller;

import com.example.second.dto.JwtRequest;
import com.example.second.dto.Response;
import com.example.second.token.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JwtController {

    @GetMapping("/")
    public @ResponseBody String greeting(){
        return "Hello Spring!";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "<h1>hello admin!</h1>";
    }

    @GetMapping("/profile")
    public @ResponseBody String userProfile(){
        return "<h1>hello user!</h1>";
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public @ResponseBody String createAuthenticationToken(@RequestBody JwtRequest requestDto){



        // Create Response
        Response response = new Response();
        System.out.println("sdcscs1");
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())

            );
        }catch(BadCredentialsException ex){
            System.out.println("sdcscs2");
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("invalid username or password");
            response.setData("No 56Token Generated");
            return response.getData();
        }
        System.out.println("sdcscs3");
        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);

        // On EveryThing Fine
        response.setData("Token :"  + jwt);
        response.setMessage("Token Generated");
        response.setStatus(HttpStatus.CREATED);
        return response.getData();
    }
}