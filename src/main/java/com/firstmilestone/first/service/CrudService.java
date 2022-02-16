package com.firstmilestone.first.service;

import javax.transaction.Transactional;

import com.firstmilestone.first.controller.CrudController;
import com.firstmilestone.first.repository.CrudRepository;
import com.firstmilestone.first.validation.UserNotFoundException;
import com.firstmilestone.first.validation.ValidateMobEml;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firstmilestone.first.entities.User;

import java.util.List;

@Service
@Transactional
public class CrudService {
	
	@Autowired
	public CrudRepository crudRepository;

	@Autowired
	private ValidateMobEml validateMobEml;

	// Get Logger for this class.
	private static Logger logger = LogManager.getLogger(CrudController.class);


	public CrudService(CrudRepository crudRepository)
	{

		this.crudRepository = crudRepository;
	}

	//  Service : Get All User From System
	public List<User> getAllUser()
	{
		logger.info("Service : Fetching Users");
		return this.crudRepository.findAll();
	}


	//  Service : Get Specific User From System
	public User getSpecificUser(String username)
	{
		User user = crudRepository.findByUserName(username);

		if(user != null)
		{
			logger.debug("Service : Fetched user with username : " + username);
			return user;
		}
		else {
			logger.error("Service : User not found with userName : " + username );
			throw new UserNotFoundException(username);
		}
	}


	//  Service : Add New User To System
	public User addNewUser(User user)
	{
		logger.info("Service : Adding New  User");
		logger.debug("Service :  Validating user details");
		boolean flag = validateMobEml.validateUser(user);

		logger.info("Service Adding User to Database");
		return crudRepository.save(user);

	}


	//  Service : Update User Details On System
	public User updateUserDetail(User newUser, String username)
	{
		logger.info("Service : Adding New  User");


		logger.debug("Service :  Find user in database");
		User user =  crudRepository.findByUserName(username);

		if(user == null) {
			logger.error("User not found");
			throw new UserNotFoundException("User not found");
		}
		else
		{
			user.setUserName(newUser.getUserName());
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setEmail(newUser.getEmail());
			user.setMobile(newUser.getMobile());
			user.setCurrentAddress(newUser.getCurrentAddress());
			user.setPermanentAddress(newUser.getPermanentAddress());
			validateMobEml.validateUpdateUser(user);
			logger.debug("Service : Updating the user details");
			crudRepository.save(user);
		}
		return user;

	}

	//  Service : Get All User From System
	public boolean deleteUser(String username)
	{
		logger.info("Service :  Providing service to API call");
		logger.warn("Service : Is user there in database ???????????????");
		// If User Not Exist
		if( !crudRepository.existsByUserName(username)) throw new UserNotFoundException(username);

		// Else delete the user
		logger.debug("User found in database and now removing it .");
		crudRepository.deleteByUserName(username);
		return true;
	}
	
	
	
	
	
	
	

}
