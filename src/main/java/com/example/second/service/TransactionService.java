package com.example.second.service;

import com.example.second.dao.TransactionRepo;
import com.example.second.dao.WalletRepo;
import com.example.second.enums.TransactionStatus;
import com.example.second.exception.WalletNotFoundException;
import com.example.second.models.Transaction;
import com.example.second.models.UserWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired 
	private WalletRepo walletRepo;

	
	
	public Transaction sendMoney(String payerM, String payeeM, double amt)
	{
		// Do both payer and payee have wallet
		UserWallet payerWallet = walletRepo.findByUserMobileNumber(payerM);
		if(payerWallet == null)
			throw new WalletNotFoundException(payerM);
		UserWallet payeeWallet = walletRepo.findByUserMobileNumber(payeeM);
		if(payeeWallet == null)
			throw new WalletNotFoundException(payeeM);

		// do transaction
		Transaction transaction = new Transaction();
		transaction.setPayee(payerWallet);
		transaction.setPayee(payeeWallet);
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
		transaction.setPayerBalance(payerWallet.getAmount());
		transaction.setPayeeBalance(payeeWallet.getAmount());
		transaction.setStatus(TransactionStatus.SUCCESS);
		return transactionRepo.save(transaction);


	}
	
	
	public TransactionStatus getStatus(long id)
	{
		Transaction t = transactionRepo.findById(id);
		return  t.getStatus();
	}
	
}
