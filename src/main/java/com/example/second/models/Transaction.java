package com.example.second.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.second.enums.TransactionStatus;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Transaction extends Timestamps{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@ManyToOne
	@JoinColumn(name = "Payer_Wallet", nullable = false)
	private UserWallet payer;

	@ManyToOne
	@JoinColumn(name = "Payee_Wallet", nullable = false)
	private UserWallet payee;

	private double amount;
	private TransactionStatus status;
	private double payerBalance;
	private double payeeBalance;
}
