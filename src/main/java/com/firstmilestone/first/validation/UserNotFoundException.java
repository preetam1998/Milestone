package com.firstmilestone.first.validation;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String username)
    {
        super("User not found with username : " + username);
    }
}
