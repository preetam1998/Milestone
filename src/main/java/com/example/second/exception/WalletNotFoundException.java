package com.example.second.exception;

public class WalletNotFoundException extends RuntimeException{
    public WalletNotFoundException(String mobileNumber)
    {
        super("No Wallet Found With Mobile Number : " + mobileNumber);
    }
}
