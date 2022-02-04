package com.example.second.service;

import com.example.second.config.Validations;
import com.example.second.dao.UserRepo;
import com.example.second.dao.WalletRepo;
import com.example.second.dto.WalletRequestDto;
import com.example.second.exception.UserNotFoundException;
import com.example.second.exception.WalletNotFoundException;
import com.example.second.exception.WalletAlreadyRegisteredException;
import com.example.second.models.User;
import com.example.second.models.UserWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WalletService {
	
	@Autowired
	private WalletRepo walletRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private Validations validations;

	public List<UserWallet> getAllWallet()
	{
		// Fetch all Wallet
		List<UserWallet> list = walletRepo.findAll();
		return list;
	}


	public double getAmount(String mobile) throws Exception
	{
		//Validation
		boolean mFlag = this.validations.checkMobile(mobile);
		if (!mFlag)
			throw new Exception("Enter Valid Mobile Number");

		// Check :: Is Wallet Present With Mobile Number
		UserWallet userWallet = walletRepo.findByUserMobileNumber(mobile);
		if(userWallet != null)
			return userWallet.getAmount();
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

		//Is Wallet Already registered with Mobile Number;
		User myUser = userRepo.findByMobileNumber(mobileNumber);
		if(myUser == null)
			throw new UserNotFoundException();

		else if(myUser.getWallet() != null)
			throw new WalletAlreadyRegisteredException(mobileNumber);

		UserWallet userWallet = new UserWallet();
		userWallet.setAmount(amt);
		userWallet.setActive(true);
		userWallet.setUser(myUser);
		myUser.setWallet(userWallet);
		walletRepo.save(userWallet);

		return myUser.getWallet();

	}
	
	
	public boolean addMoneyToWallet(String mobileNumber, double amt) throws Exception {

		// Validation
		boolean mFlag = this.validations.checkMobile(mobileNumber);
		boolean aFlag = this.validations.checkAmount(amt);

		if( !mFlag || !aFlag)
			throw new Exception("Check Your Mobile Number or Amount Entered !");

		// Fetch Wallet and add money to it
		UserWallet userWallet = walletRepo.findByUserMobileNumber(mobileNumber);

		// Wallet associated with mobile
		if(userWallet == null)
				throw new WalletNotFoundException(mobileNumber);

		userWallet.setAmount(userWallet.getAmount() + amt);
		walletRepo.save(userWallet);

		// Update the user wallet also
		User myUser = userRepo.findByUserName(mobileNumber);
		myUser.setWallet(userWallet);
		return true;
	}

	public boolean deleteWallet(String mobileNumber) throws Exception {

		//validation
		boolean mFlag = this.validations.checkMobile(mobileNumber);
		if (!mFlag)
			throw new Exception("Check Your Mobile Number Entered !");

		// Fetch Wallet
		UserWallet userWallet = walletRepo.findByUserMobileNumber(mobileNumber);

		// Wallet associated with mobile
		if (userWallet == null)
			throw new WalletNotFoundException(mobileNumber);

		//Delete Wallet by mobile Number
		walletRepo.deleteByUserMobileNumber(mobileNumber);

		// Update the user wallet also
		User myUser = userRepo.findByUserName(mobileNumber);
		myUser.setWallet(null);
		myUser.setActive(false);
		return true;
	}
}
