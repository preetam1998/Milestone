package com.example.second.dto;

import com.example.second.enums.TransactionType;
import com.example.second.enums.TransactionStatus;
import com.example.second.models.Transaction;
import lombok.Data;

import java.util.Date;


@Data
public class TransactionResponseDto {

    private int transactionId;
    private TransactionStatus status;
    private TransactionType type;
    private int userWalletId;
    private double amount;
    private double totalBalance;
    private Date createTime;
    private  Date updateTime;


    public TransactionResponseDto(Transaction transaction, int userWalletId)
    {
        this.status = transaction.getStatus();
        this.createTime = transaction.getCreateTime();
        this.updateTime = transaction.getUpdateTime();
    }


}
