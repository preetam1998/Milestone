package com.example.second.controller;

import com.example.second.dto.Response;
import com.example.second.models.User;
import com.example.second.models.UserSideResponse;
import com.example.second.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	// Get Logger for the Class
	private static Logger logger  = LogManager.getLogger(UserController.class);

	@GetMapping("/all")
	public Response getAllUser()
	{
		logger.info("Controller : API Call For Fetching All User Received By Controller.");
		logger.warn("Controller : Getting All User  for API Request");

		// Fetch All Users from DB
		List<User> users = this.userService.getAllUser();

		//Create Response
		Response response = new Response();
		response.setMessage("Fetched All Users");

		if(users != null)
		{
			response.setStatus(HttpStatus.OK);
			response.setData(users.toString());
			logger.debug("Controller : Users : "  + response.getData());
		}
		else
		{
			response.setStatus(HttpStatus.NO_CONTENT);
			response.setData("No User Present");
			logger.error("Controller : No User Found");
		}
		return response;
	}
	
	@GetMapping("/{mobileNumber}")
	public Response getUser(@PathVariable String mobileNumber )
	{
		logger.info("Controller : API Call For Getting Specific User Reached to Controller");
		logger.warn("Controller : Fetch User with mobile number : " + mobileNumber );

		//Fetching User Using Mobile Number
		User users = this.userService.getSpecificUser(mobileNumber);

		//Create Response
		Response response = new Response();
		response.setMessage("Fetching  Users with Mobile Number : " + mobileNumber);

		if(users != null)
		{
			response.setStatus(HttpStatus.OK);
			response.setData(users.toString());
			logger.debug("Controller : User with Mobile Number : " + mobileNumber + " received .");
		}
		else
		{
			response.setStatus(HttpStatus.NO_CONTENT);
			response.setData("No User Present");
			logger.error("Controller : User not found with mobileNumber : " + mobileNumber );
		}
		return response;
	}

	
	@PostMapping("/addNewUser")
	public Response createUser(@RequestBody UserSideResponse user)
	{
		logger.info("Controller : API Call For Getting Specific User Reached to Controller");
		logger.warn("Controller : Creating User for UserDetails :"  + user.toString());

		// Adding New User in to  DB
		System.out.println(user);
		User userAdded = this.userService.addNewUser(user);

		//Create Response
		Response response = new Response();
		response.setMessage("Adding the New User");

		if(userAdded != null)
		{
			response.setStatus(HttpStatus.CREATED);
			response.setData(userAdded.toString());
			logger.debug("Controller : Adding  user with Mobile Number : " + user.getMobileNumber() );
		}
		else
		{
			response.setStatus(HttpStatus.NO_CONTENT);
			response.setData("User Not Added");
			logger.error("Controller : User not Added with mobile Number : " + user.getMobileNumber());

		}
		return response;
		
	}
	
	@PutMapping("/{userName}")
	public Response updateUser(@PathVariable String userName, @RequestBody UserSideResponse user)
	{
		logger.info("Controller : API Call For Updating  Specific User Reached to Controller");
		logger.warn("Controller : Updating user with details : " + user.toString());

		// Updating User in to  DB
		User userUpdate = this.userService.updateUser(userName, user);

		//Create Response
		Response response = new Response();
		response.setMessage("Updating the  User");

		if(userUpdate != null)
		{
			response.setStatus(HttpStatus.OK);
			response.setData(userUpdate.toString());
			logger.debug("Controller : Updating  user with Mobile Number : " + user.getMobileNumber() );
		}
		else
		{
			response.setStatus(HttpStatus.NO_CONTENT);
			response.setData("User Not Updated");
		}
		return response;
	}
	
	
	@DeleteMapping("/{mobileNumber}")
	public Response deleteUser(@PathVariable String mobileNumber)
	{
		logger.info("Controller : API Call For Deleting Specific User Reached to Controller");
		logger.warn("Controller : Deleting User with mobile : " + mobileNumber);

		boolean flag = this.userService.deleteUser(mobileNumber);
		Response response = new Response();
		response.setMessage("Deleting  the  User");

		if(flag )
		{
			response.setStatus(HttpStatus.OK);
			response.setData("User with mobileNumber : " + mobileNumber + " deleted Successfully");
			logger.debug("Controller : Deleting   user with Mobile Number : " + mobileNumber );
		}
		return response;

	}

}
