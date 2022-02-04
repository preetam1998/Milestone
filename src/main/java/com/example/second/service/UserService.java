package com.example.second.service;

import com.example.second.config.Validations;
import com.example.second.dao.WalletRepo;
import com.example.second.exception.UserNotFoundException;
import com.example.second.exception.UserValidationException;
import com.example.second.models.User;
import com.example.second.models.UserWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.second.dao.UserRepo;
import com.example.second.models.UserSideResponse;

import java.util.List;

import javax.transaction.Transactional;

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
			UserWallet w1 = walletRepo.findByUserMobileNumber(user.getMobileNumber());
			if (w1 != null)
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

		if(uPresent == null)
				throw new UserNotFoundException();

		else
		{
			// Validation
			boolean userFlag = this.userValidationException.checkUserUpdateValidation(user);

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

				userRepo.save(u1);
			}
			return u1;
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
			throw new UserNotFoundException();

		return true;
	}

}