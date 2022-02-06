package com.example.second.service;

import com.example.second.dao.TransactionRepo;
import com.example.second.dao.WalletRepo;
import com.example.second.enums.TransactionStatus;
import com.example.second.exception.WalletNotFoundException;
import com.example.second.models.Transaction;
import com.example.second.models.UserWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired 
	private WalletRepo walletRepo;

	
	
	public Transaction sendMoney(String payerM, String payeeM, double amt)
	{
		System.out.println(payeeM);
		System.out.println(payerM);
		System.out.println(amt);

		// Do both payer and payee have wallet
		UserWallet payerWallet = walletRepo.findByMobileNumber(payerM);
		if(payerWallet == null)
			throw new WalletNotFoundException(payerM);
		UserWallet payeeWallet = walletRepo.findByMobileNumber(payeeM);
		if(payeeWallet == null)
			throw new WalletNotFoundException(payeeM);

		// do transaction
		Transaction transaction = new Transaction();
		transaction.setAmount(amt);
		transaction.setPayer(payerWallet.getMobileNumber());
		transaction.setPayee(payeeWallet.getMobileNumber());
		transaction.setStatus(TransactionStatus.PENDING);
		transactionRepo.save(transaction);

		//checking balance
		if(payerWallet.getAmount() < amt)
		{
			transaction.setStatus(TransactionStatus.FAILED);
			return  transactionRepo.save(transaction);
		}

		//Updating Balance of Both
		payerWallet.setAmount(payerWallet.getAmount() - amt);
		payeeWallet.setAmount(payeeWallet.getAmount() + amt);
		walletRepo.save(payerWallet);
		walletRepo.save(payeeWallet);

		// Make transaction Successful
		transaction.setStatus(TransactionStatus.SUCCESS);

		return transactionRepo.save(transaction);
	}


	public List<Transaction> getTransactionHistory(String mobileNumber)
	{

		List<Transaction> transactions = transactionRepo.findByMobileNumber(mobileNumber);

		return transactions;
	}
	

	
}
