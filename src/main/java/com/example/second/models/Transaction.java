package com.example.second.models;

import com.example.second.enums.TransactionStatus;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Transaction extends Timestamps{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//	@ManyToOne
//	@JoinColumn(name = "payer_wallet_id", nullable = false)
//	private UserWallet payer;
//
//	@ManyToOne
//	@JoinColumn(name = "payee_wallet_id", nullable = false)
//	private UserWallet payee;

	private double amount;
	private TransactionStatus status;
	private String payer;
	private String payee;
}
