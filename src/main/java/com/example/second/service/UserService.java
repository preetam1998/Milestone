package com.example.second.service;

import com.example.second.config.Validations;
import com.example.second.dao.UserRepo;
import com.example.second.dao.WalletRepo;
import com.example.second.exception.UserNotFoundException;
import com.example.second.exception.UserValidationException;
import com.example.second.models.User;
import com.example.second.models.UserSideResponse;
import com.example.second.models.UserWallet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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


	// Get Logger for the class
	private  static final Logger logger = LogManager.getLogger(UserService.class);

	public List<User> getAllUser()
	{
		logger.info("Service : Providing Service to API ");
		logger.warn("Service : Fetching All User form Database");
		return this.userRepo.findAll();
	}
	
	public User getSpecificUser(String mobile)
	{

		logger.info("Service : Providing Service to API ");
		logger.warn("Service : Fetching All User form Database");

		//Validation
		boolean mFlag = this.validations.checkMobile(mobile);


		if(mFlag)
		{


			// Fetch  Specific User....
			User u = userRepo.findByMobileNumber(mobile) ;
			logger.debug("Service : Fetching user with mobile number  : " + mobile);

			return u;
		}
		else
		{
			logger.error("Service : Enter Valid Mobile Number");
			throw new RuntimeException("Enter Valid Mobile Number");
		}

	}
	
	public User addNewUser(UserSideResponse user)
	{

		logger.info("Service : Providing Service to API ");
		logger.warn("Service : Fetching All User form Database");

		// Validation
		logger.info("Service : Validating user details before adding to database.");
		boolean userFlag = this.userValidationException.checkUserValidation(user);

		User u1 = new User();
		if (userFlag) {
			// Add details
			logger.info("Service : Valid  user details  ");

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
			logger.info("Service : Checking whether  user has wallet or not.");

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

		logger.info("Service : Providing Service to API ");
		logger.warn("Service : Fetching  User and Updating the Details of user then saving to Database");


		// Check weather user is present of not??/
		User uPresent = userRepo.findByUserName(username);

		if(uPresent == null) {
			logger.info("Service : User not present in database");
			throw new UserNotFoundException(user.getMobileNumber());
		}
		else
		{
			logger.info("Service : User fetched from database :: " + uPresent.getFirstName());
			// Validation
			logger.info("Service : Validating user details before updating it");
			boolean userFlag = this.userValidationException.checkUserUpdateValidation(user);


			if (userFlag) {
				logger.info("Service : User details are valid , now updating its details.");
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

		logger.info("Service : Providing Service to API ");
		logger.warn("Service : Fetching All User form Database");

		// Validation
		boolean mFlag = this.validations.checkMobile(mobile);

		if(mFlag)
		{

			User u1 = userRepo.findByMobileNumber(mobile);
			if (u1 != null) {
				logger.debug("Service : Fetching User with mobile number : " + mobile);
				userRepo.delete(u1);
			}
			else
			{
				logger.error("Service : User Not Found with mobile number : "  + mobile);
			}
		}
		else {
			logger.error("Service : Enter Valid Mobile Number");
			throw new UserNotFoundException(mobile);
		}

		return true;
	}

}