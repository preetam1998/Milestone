package com.example.second.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException()
    {
        super("User not found");
    }
}
