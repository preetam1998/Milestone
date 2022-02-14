package com.example.second.service;

import com.example.second.config.Validations;
import com.example.second.dao.UserRepo;
import com.example.second.dao.WalletRepo;
import com.example.second.dto.WalletRequestDto;
import com.example.second.exception.UserNotFoundException;
import com.example.second.exception.WalletAlreadyRegisteredException;
import com.example.second.exception.WalletNotFoundException;
import com.example.second.models.User;
import com.example.second.models.UserWallet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WalletService {
	
	@Autowired
	private WalletRepo walletRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private Validations validations;

	//Get Logger for this class
	private static Logger logger = LogManager.getLogger(TransactionService.class);


	public UserWallet getWallet(String mobile) throws Exception
	{
		logger.info("Service : Providing service to API : getWallet request");
		logger.warn("Service : Fetching Wallet : " + mobile);
		//Validation
		boolean mFlag = this.validations.checkMobile(mobile);

		if (!mFlag) {
			logger.error("Service : Invalid Mobile Number : " + mobile);
			throw new Exception("Enter Valid Mobile Number");
		}

		// Check :: Is Wallet Present With Mobile Number
		UserWallet userWallet = walletRepo.findByMobileNumber(mobile);
		if(userWallet != null) {
			logger.debug("Service : Wallet With Mobile Number : " + mobile +  ", Wallet : " + userWallet);
			return userWallet;
		}
		else {
			logger.error("Service : Wallet Not Found With Mobile Number  : " + mobile);
			throw new WalletNotFoundException(mobile);
		}
	}

	public UserWallet createWallet(WalletRequestDto w) throws Exception
	{
		logger.info("Service : Providing service to API create-wallet request ");
		logger.warn("Service : Creating Wallet for user with wallet details: " + w.toString());
		String mobileNumber = w.getMobileNumber();
		double amt = w.getAmount();

		// Validation
		boolean mFlag = this.validations.checkMobile(mobileNumber);
		boolean aFlag = this.validations.checkAmount(amt);

		if( !mFlag || !aFlag)
		{
			logger.error("Service : Check Your Mobile Number or Amount Entered ");
			throw new Exception("Check Your Mobile Number or Amount Entered !");
		}

		//Is User Present  with Mobile Number;
		User myUser = userRepo.findByMobileNumber(mobileNumber);
		if(myUser == null) {
			logger.error("Service : User Not Found With Mobile Number : " + w.getMobileNumber());
			throw new UserNotFoundException(mobileNumber);
		}

		// Check weather Wallet already present with mobile number
		UserWallet myWallet = walletRepo.findByMobileNumber(mobileNumber);
		if(myWallet != null){
			logger.error("Service : You already have wallet with mobile number : " + w.getMobileNumber());
			throw new WalletAlreadyRegisteredException(mobileNumber);
		}



		// Create Wallet
		myWallet = new UserWallet();
		myWallet.setAmount(amt);
		myWallet.setActive(true);
		myWallet.setMobileNumber(mobileNumber);
		walletRepo.save(myWallet);

		// Make User Active too.
		myUser.setActive(true);
		userRepo.save(myUser);
		logger.debug("Service : Wallet created with details" + myWallet.toString());
		return myWallet;

	}
	
	public boolean addMoneyToWallet(String mobileNumber, double amt) throws Exception
	{

		logger.info("Service : Providing service to API add-money-wallet request ");
		logger.warn("Service : Adding money : " + amt + " to wallet : " + mobileNumber);
		// Validation
		boolean mFlag = this.validations.checkMobile(mobileNumber);
		boolean aFlag = this.validations.checkAmount(amt);

		if( !mFlag || !aFlag)
		{
			logger.error("Service : check Mobile Number or Amount");
			throw new Exception("Check Your Mobile Number or Amount Entered !");
		}


		// Fetch Wallet and add money to it
		UserWallet userWallet = walletRepo.findByMobileNumber(mobileNumber);

		// Wallet associated with mobile
		if(userWallet == null)
		{
			logger.error("Service : Wallet not found for mobile number : " + mobileNumber);
			throw new WalletNotFoundException(mobileNumber);
		}

		userWallet.setAmount(userWallet.getAmount() + amt);
		walletRepo.save(userWallet);
		logger.debug("Service : Amount Added to Wallet , Wallet Details  : " + userWallet.toString());

		return true;
	}

}
