package com.example.second.exception;

public class WalletAlreadyRegisteredException extends RuntimeException{
    public WalletAlreadyRegisteredException(String mobileNumber)
    {
        super("Wallet Already registered with mobile NUmber : " + mobileNumber);
    }
}
