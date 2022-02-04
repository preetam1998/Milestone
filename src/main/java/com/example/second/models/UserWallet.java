package com.example.second.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class UserWallet extends Timestamps{

    @Id
    private String mobileNumber;
    private double amount;
    private boolean isActive;


//    @JsonIgnore
//    @OneToOne(mappedBy = "wallet", fetch = FetchType.LAZY)
//    private User user;

//    @JsonIgnore
//    @OneToMany(mappedBy = "payer")
//    private List<Transaction> payerTransaction;
//
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "payee")
//    private List<Transaction>payeeTransaction;
}
