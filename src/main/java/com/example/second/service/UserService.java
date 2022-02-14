package com.example.second.service;

import com.example.second.config.Validations;
import com.example.second.dao.UserRepo;
import com.example.second.dao.WalletRepo;
import com.example.second.exception.UserNotFoundException;
import com.example.second.exception.UserValidationException;
import com.example.second.models.User;
import com.example.second.models.UserSideResponse;
import com.example.second.models.UserWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

	public UserService() {
	}

	@Autowired
	private WalletRepo walletRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private Validations validations;

	@Autowired
	private UserValidationException userValidationException ;



	public List<User> getAllUser()
	{
		return this.userRepo.findAll();
	}
	
	public User getSpecificUser(String mobile)
	{
		//Validation
		boolean mFlag = this.validations.checkMobile(mobile);

		if(mFlag)
		{
			// Fetch  Specific User....
			User u = userRepo.findByMobileNumber(mobile) ;
			return u;
		}
		else
		{
			throw new RuntimeException("Enter Valid Mobile Number");
		}

	}
	
	public User addNewUser(UserSideResponse user)
	{
		// Validation
		boolean userFlag = this.userValidationException.checkUserValidation(user);

		User u1 = new User();
		if (userFlag) {
			// Add details
			u1.setUserName(user.getUserName());
			u1.setPassword(user.getPassword());
			u1.setFirstName(user.getFirstName());
			u1.setLastName(user.getLastName());
			u1.setEmail(user.getEmail());
			u1.setMobileNumber(user.getMobileNumber());
			u1.setCAddress(user.getcAddress());
			u1.setPAddress(user.getpAddress());
			u1.setRoles("USER");

			// Check the Wallet with  Number
			UserWallet myWallet = walletRepo.findByMobileNumber(user.getMobileNumber());
			if (myWallet != null)
				u1.setActive(true);
			else
				u1.setActive(false);

			userRepo.save(u1);
		}
		return u1;
	}

	public User updateUser(String username, UserSideResponse user)
	{

		// Check weather user is present of not??/
		User uPresent = userRepo.findByUserName(username);
		System.out.println(uPresent);
		if(uPresent == null)
				throw new UserNotFoundException(user.getMobileNumber());

		else
		{
			// Validation
			boolean userFlag = this.userValidationException.checkUserUpdateValidation(user);


			if (userFlag) {
				// Add details
				uPresent.setUserName(user.getUserName());
				uPresent.setPassword(user.getPassword());
				uPresent.setFirstName(user.getFirstName());
				uPresent.setLastName(user.getLastName());
				uPresent.setEmail(user.getEmail());
				uPresent.setMobileNumber(user.getMobileNumber());
				uPresent.setCAddress(user.getcAddress());
				uPresent.setPAddress(user.getpAddress());

				userRepo.save(uPresent);
			}
			return uPresent;
		}
	}

	public boolean deleteUser(String mobile )
	{
		// Validation
		boolean mFlag = this.validations.checkMobile(mobile);

		if(mFlag)
		{
			User u1 = userRepo.findByMobileNumber(mobile);
			if (u1 != null)
				userRepo.delete(u1);
		}
		else
			throw new UserNotFoundException(mobile);

		return true;
	}

}