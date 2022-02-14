package com.example.second.service;

import com.example.second.dao.TransactionRepo;
import com.example.second.dao.WalletRepo;
import com.example.second.enums.TransactionStatus;
import com.example.second.exception.WalletNotFoundException;
import com.example.second.models.Transaction;
import com.example.second.models.UserWallet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired 
	private WalletRepo walletRepo;

	//Get Logger for this class
	private static Logger logger = LogManager.getLogger(TransactionService.class);
	
	public Transaction sendMoney(String payerM, String payeeM, double amt)
	{
		logger.info("Service : Providing Service to API Request");
		logger.warn("Service : Send Money with details  Payer : " + payerM + ", Payee : " + payeeM + ", Amount : " + amt );

		// Do both payer and payee have wallet
		UserWallet payerWallet = walletRepo.findByMobileNumber(payerM);
		if(payerWallet == null) {
			logger.error("Service : No Wallet With Mobile Number : " + payerM);
			throw new WalletNotFoundException(payerM);
		}
		UserWallet payeeWallet = walletRepo.findByMobileNumber(payeeM);
		if(payeeWallet == null) {
			logger.error("Service : No Wallet With Mobile Number : " + payeeM);
			throw new WalletNotFoundException(payeeM);
		}

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
			logger.debug("Service : " + " Wallet Balance : " + payerWallet.getAmount() + ", Requested Amount  : " + amt);
			logger.error("Service :  Wallet balance of Payer  With Mobile Number : " + payerM , " Amount : " + payerWallet.getAmount());

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

		logger.debug("Service : Transaction Successful " + " Details : " + transaction.toString());
		return transactionRepo.save(transaction);
	}


	public List<Transaction> getTransactionHistory(String mobileNumber)
	{
		logger.info("Service : Servicing the API request ");
		logger.warn("Service : Getting History for wallet : " + mobileNumber);
		List<Transaction> transactions = transactionRepo.findByMobileNumber(mobileNumber);

		logger.debug("Service : History : " + transactions);
		return transactions;
	}
	

	
}
