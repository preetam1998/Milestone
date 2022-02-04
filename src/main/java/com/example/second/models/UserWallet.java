package com.example.second.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonIgnore
    @OneToOne(mappedBy = "wallet", fetch = FetchType.LAZY)
    private User user;

    private double amount;

    private boolean isActive;

    @JsonIgnore
    @OneToMany(mappedBy = "payer")
    private List<Transaction> payerTransaction;


    @JsonIgnore
    @OneToMany(mappedBy = "payee")
    private List<Transaction>payeeTransaction;
}
