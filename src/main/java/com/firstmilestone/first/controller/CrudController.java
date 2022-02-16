package com.firstmilestone.first.controller;

import com.firstmilestone.first.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.firstmilestone.first.entities.User;
import com.firstmilestone.first.service.CrudService;


@RestController
@RequestMapping(path="/user")
public class CrudController {


	@Autowired
	public CrudService crudService;

	// Get Logger for this class.
	private static Logger logger = LogManager.getLogger(CrudController.class);


	@GetMapping(path = "/all")
	public Response getAllUser()
	{

		logger.info("Controller :  Received API call for fetching all user.");
		logger.warn("Controller :  providing service to call");
		// Fetching All Users from System
		Iterable<User> list = crudService.getAllUser();

		//Setting Up the Response
		Response res = new Response();
		res.setStatus(HttpStatus.OK);
		res.setJob("Fetching All Users");



		if(list != null)
		{
			logger.debug("Controller :  Users fetched :: " + list.toString());
			res.setData(list.toString());
		}
		else
		{
			res.setData("No Users");
			logger.error("Controller : No User Found !!");
		}
		return res;
	}
	
	@GetMapping(path = "/{username}")
	public Response getSpecificUser(@PathVariable String username)
	{

		logger.info("Controller :  Received API call for fetching all user.");
		logger.warn("Controller :  providing service to call");


		// Fetching Specific User from System
		User user  = this.crudService.getSpecificUser(username);

		//Setting Up the Response
		Response res = new Response();
		res.setJob("Fetching user   by its username ");

		if(user != null)
		{
			logger.debug("Controller :  User fetched :: " + user.toString());
			res.setStatus(HttpStatus.OK);
			res.setData(user.toString());
		}
		else
		{
			res.setStatus(HttpStatus.NOT_FOUND);
			res.setData("No User Found with username : " + username);
			logger.error("Controller : No User Found with username : " + username);
		}
		return res;
	}
	
	@PostMapping()
	public Response  addNewUser(@RequestBody User newUser)
	{
		logger.info("Controller :  Received API call for fetching all user.");
		logger.warn("Controller :  providing service to call");

		// Adding new user to system
		User user  = this.crudService.addNewUser(newUser);

		//Setting Up the Response
		Response res = new Response();

		res.setJob("Adding New User");

		if(user != null)
		{
			logger.debug("Controller :  User Added :: " + user.toString());
			res.setStatus(HttpStatus.OK);
			res.setData(user.toString());
		}
		else
		{
			res.setStatus(HttpStatus.NOT_ACCEPTABLE);
			res.setData("Sorry, Check Your Credentials Please ......! ");
			logger.error("Controller : No User Added with username : " + newUser.getUserName());
		}
		return res;
	}
	
	@PutMapping(path = "/{username}")
	public Response updateUser(@RequestBody User updateUser,@PathVariable String username)
	{

		logger.info("Controller :  Received API call for fetching all user.");
		logger.warn("Controller :  providing service to call");

		// Updating  the  user
		User user  = this.crudService.updateUserDetail(updateUser, username);

		//Setting Up the Response
		Response res = new Response();
		res.setJob("Updating User Details");

		if(user != null)
		{
			logger.debug("Controller :  User Updated Details :: " + user.toString());
			res.setStatus(HttpStatus.OK);
			res.setData(user.toString());
		}
		else
		{
			res.setStatus(HttpStatus.CONFLICT);
			res.setData("Sorry, Check Your Credentials Please ......! ");
			logger.error("Controller : No User Added with username : " + username);
		}
		return res;
	}
	
	@DeleteMapping("/{username}")
	public Response deleteUser(@PathVariable String username )
	{

		logger.info("Controller :  Received API call for fetching all user.");
		logger.warn("Controller :  providing service to call");

		//Deleting the User by its username
		boolean flag = crudService.deleteUser(username);

		//Create Response
		Response res = new Response();
		res.setJob("Deleting  User With Username");
		if(flag)
		{
			res.setStatus(HttpStatus.OK);
			res.setData("Deleting the User :: Successful");
		}
		return  res;
	}
}
