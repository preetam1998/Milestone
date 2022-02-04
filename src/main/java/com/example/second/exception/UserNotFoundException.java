package com.example.second.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String mobile )
    {
        super("User not found with mobile number : " +mobile );
    }
}
