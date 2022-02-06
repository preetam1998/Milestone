package com.example.second.dto;

import com.example.second.enums.TransactionStatus;
import com.example.second.models.Transaction;
import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TransactionResponseDto {


    private double amount;
    private String payer;
    private String payee;
    private TransactionStatus status;



    public TransactionResponseDto(Transaction transaction)
    {
        this.status = transaction.getStatus();
        this.amount = transaction.getAmount();
        this.payee = transaction.getPayee();
        this.payer = transaction.getPayer();

    }

}
