package com.example.second.controller;

import com.example.second.dto.Response;
import com.example.second.models.User;
import com.example.second.models.UserSideResponse;
import com.example.second.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;



	@GetMapping("/all")
	public Response getAllUser()
	{
		// Fetch All Users from DB
		List<User> users = this.userService.getAllUser();

		//Create Response
		Response response = new Response();
		response.setMessage("Fetching All Users");
		if(users != null)
		{
			response.setStatus(HttpStatus.OK);
			response.setData(users.toString());
		}
		else
		{
			response.setStatus(HttpStatus.NO_CONTENT);
			response.setData("No User Present");
		}
		return response;
	}
	
	@GetMapping("/{mobileNumber}")
	public Response getUser(@PathVariable String mobileNumber )
	{
		//Fetching User Using Mobile Number
		User users = this.userService.getSpecificUser(mobileNumber);

		//Create Response
		Response response = new Response();
		response.setMessage("Fetching  Users with Mobile Number : " + mobileNumber);
		if(users != null)
		{
			response.setStatus(HttpStatus.OK);
			response.setData(users.toString());
		}
		else
		{
			response.setStatus(HttpStatus.NO_CONTENT);
			response.setData("No User Present");
		}
		return response;
	}

	
	@PostMapping("/addNewUser")
	public Response createUser(@RequestBody UserSideResponse user)
	{
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
		}
		else
		{
			response.setStatus(HttpStatus.NO_CONTENT);
			response.setData("User Not Added");
		}
		return response;
		
	}
	
	@PutMapping("/{userName}")
	public Response updateUser(@PathVariable String userName, @RequestBody UserSideResponse user)
	{
		// Updating User in to  DB
		System.out.println(user );
		System.out.println("user" );
		User userUpdate = this.userService.updateUser(userName, user);

		//Create Response
		Response response = new Response();
		response.setMessage("Updating the  User");
		if(userUpdate != null)
		{
			response.setStatus(HttpStatus.OK);
			response.setData(userUpdate.toString());
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

		boolean flag = this.userService.deleteUser(mobileNumber);

		Response response = new Response();
		response.setMessage("Deleting  the  User");
		if(flag )
		{
			response.setStatus(HttpStatus.OK);
			response.setData("User with mobileNumber : " + mobileNumber + " deleted Successfully");
		}
		return response;

	}

}
