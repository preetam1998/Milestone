package com.firstmilestone.first.controller;

import com.firstmilestone.first.response.Response;
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
	
	@GetMapping(path = "/all")
	public Response getAllUser()
	{
		// Fetching All Users from System
		Iterable<User> list = crudService.getAllUser();

		//Setting Up the Response
		Response res = new Response();
		res.setStatus(HttpStatus.OK);
		res.setJob("Fetching All Users");

		if(list != null)
		{
			res.setData(list.toString());
		}
		else
		{
			res.setData("No Users");
		}
		return res;
	}
	
	@GetMapping(path = "/{username}")
	public Response getSpecificUser(@PathVariable String username)
	{
		// Fetching Specific User from System
		User user  = this.crudService.getSpecificUser(username);

		//Setting Up the Response
		Response res = new Response();
		res.setJob("Fetching user   by its username ");

		if(user != null)
		{
			res.setStatus(HttpStatus.OK);
			res.setData(user.toString());
		}
		else
		{
			res.setStatus(HttpStatus.NOT_FOUND);
			res.setData("No User Found with username : " + username);
		}
		return res;
	}
	
	@PostMapping()
	public Response  addNewUser(@RequestBody User newUser)
	{

		// Adding new user to system
		User user  = this.crudService.addNewUser(newUser);

		//Setting Up the Response
		Response res = new Response();

		res.setJob("Adding New User");

		if(user != null)
		{
			res.setStatus(HttpStatus.OK);
			res.setData(user.toString());
		}
		else
		{
			res.setStatus(HttpStatus.NOT_ACCEPTABLE);
			res.setData("Sorry, Check Your Credentials Please ......! ");
		}
		return res;
	}
	
	@PutMapping(path = "/{username}")
	public Response updateUser(@RequestBody User updateUser,@PathVariable String username)
	{

		// Updating  the  user
		User user  = this.crudService.updateUserDetail(updateUser, username);

		//Setting Up the Response
		Response res = new Response();
		res.setJob("Updating User Details");

		if(user != null)
		{
			res.setStatus(HttpStatus.OK);
			res.setData(user.toString());
		}
		else
		{
			res.setStatus(HttpStatus.CONFLICT);
			res.setData("Sorry, Check Your Credentials Please ......! ");
		}
		return res;
	}
	
	@DeleteMapping("/{username}")
	public Response deleteUser(@PathVariable String username )
	{
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
