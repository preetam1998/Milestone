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



	public UserWallet getWallet(String mobile) throws Exception
	{
		//Validation
		boolean mFlag = this.validations.checkMobile(mobile);
		if (!mFlag)
			throw new Exception("Enter Valid Mobile Number");

		// Check :: Is Wallet Present With Mobile Number
		UserWallet userWallet = walletRepo.findByMobileNumber(mobile);
		if(userWallet != null)
			return userWallet;
		else
			throw new WalletNotFoundException(mobile);
	}

	public UserWallet createWallet(WalletRequestDto w) throws Exception
	{
		
		String mobileNumber = w.getMobileNumber();
		double amt = w.getAmount();

		// Validation
		boolean mFlag = this.validations.checkMobile(mobileNumber);
		boolean aFlag = this.validations.checkAmount(amt);

		if( !mFlag || !aFlag)
			throw new Exception("Check Your Mobile Number or Amount Entered !");

		//Is User Present  with Mobile Number;
		User myUser = userRepo.findByMobileNumber(mobileNumber);
		if(myUser == null)
			throw new UserNotFoundException(mobileNumber);

		// Check weather Wallet already present with mobile number
		UserWallet myWallet = walletRepo.findByMobileNumber(mobileNumber);
		if(myWallet != null)
			throw new WalletAlreadyRegisteredException(mobileNumber);


		// Create Wallet
		myWallet = new UserWallet();
		myWallet.setAmount(amt);
		myWallet.setActive(true);
		myWallet.setMobileNumber(mobileNumber);
		walletRepo.save(myWallet);

		// Make User Active too.
		myUser.setActive(true);
		userRepo.save(myUser);

		return myWallet;

	}
	
	public boolean addMoneyToWallet(String mobileNumber, double amt) throws Exception
	{

		// Validation
		boolean mFlag = this.validations.checkMobile(mobileNumber);
		boolean aFlag = this.validations.checkAmount(amt);

		if( !mFlag || !aFlag)
			throw new Exception("Check Your Mobile Number or Amount Entered !");

		// Fetch Wallet and add money to it
		UserWallet userWallet = walletRepo.findByMobileNumber(mobileNumber);

		// Wallet associated with mobile
		if(userWallet == null)
				throw new WalletNotFoundException(mobileNumber);

		userWallet.setAmount(userWallet.getAmount() + amt);
		walletRepo.save(userWallet);
		return true;
	}

}
