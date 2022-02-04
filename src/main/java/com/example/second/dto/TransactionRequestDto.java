package com.example.second.dto;

import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionRequestDto {
    private String payerMobile;
    private String payeeMobile;
    private double amount;
}