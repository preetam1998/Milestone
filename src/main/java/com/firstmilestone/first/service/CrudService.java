package com.firstmilestone.first.service;

import javax.transaction.Transactional;
import com.firstmilestone.first.repository.CrudRepository;
import com.firstmilestone.first.validation.UserNotFoundException;
import com.firstmilestone.first.validation.ValidateMobEml;
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


 public CrudService(CrudRepository crudRepository)
	{
		this.crudRepository = crudRepository;
	}

	//  Service : Get All User From System
	public List<User> getAllUser()
	{
		return this.crudRepository.findAll();
	}


	//  Service : Get Specific User From System
	public User getSpecificUser(String username)
	{
		//return crudRepository.findByUserName(username).orElseThrow( () -> new UserNotFoundException(username));
		User user = crudRepository.findByUserName(username);

		if(user != null)
		{
			return user;
		}
		else
			throw new UserNotFoundException(username);
	}


	//  Service : Add New User To System
	public User addNewUser(User user)
	{
		validateMobEml.validateUser(user);
		return crudRepository.save(user);
	}


	//  Service : Update User Details On System
	public User updateUserDetail(User newUser, String username)
	{
		User user =  crudRepository.findByUserName(username);

		if(user == null) throw new UserNotFoundException("User not found");
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
			crudRepository.save(user);
		}
		return user;

	}

	//  Service : Get All User From System
	public boolean deleteUser(String username)
	{
		// If User Not Exist
		if( !crudRepository.existsByUserName(username)) throw new UserNotFoundException(username);

		// Else delete the user
		crudRepository.deleteByUserName(username);
		return true;
	}
	
	
	
	
	
	
	

}
