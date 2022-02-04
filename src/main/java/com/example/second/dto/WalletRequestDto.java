package com.example.second.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletRequestDto {
    private String mobileNumber;
    private double amount;
}
