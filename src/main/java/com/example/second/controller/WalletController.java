package com.example.second.controller;

import com.example.second.dto.Response;
import com.example.second.dto.WalletRequestDto;
import com.example.second.models.UserWallet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.second.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RestController
@RequestMapping("/wallet")
public class WalletController {
	
	@Autowired 
	private WalletService wService;


	@GetMapping("/all")
	public Response getAllWallet()
	{
		// Fetch wallet
		List<UserWallet> wallets = this.wService.getAllWallet();

		//Create Response
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Fetched All Wallets");
		response.setData(wallets.toString());
		return response;
	}

	@GetMapping("/check-balance/{mobile}")
	public Response getAmount( @PathVariable String mobile) throws Exception {

		// check Total Balance on Wallet
		double amount =  this.wService.getAmount(mobile);

		//Create Response
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Fetched All Wallets");
		response.setData(String.valueOf(amount));
		return response;
	}

	@PostMapping("/create-wallet")
	public Response createWallet(@RequestBody WalletRequestDto wallet) throws Exception {

		// Create Wallet
		UserWallet userWallet = this.wService.createWallet(wallet);
		System.out.println(wallet);

		//Create Response
		Response response = new Response();
		response.setStatus(HttpStatus.CREATED);
		response.setMessage("Wallet Created");
		response.setData(userWallet.toString());
		return response;
	}
	
	@PutMapping("/add-balance/{mobile}")
	public Response addAmtToWallet(@PathVariable String mobile, @RequestBody WalletRequestDto walletRequest) throws Exception {
		boolean flag = this.wService.addMoneyToWallet(mobile, walletRequest.getAmount());
		//Create Response
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Adding Money To Wallet");
		response.setData("Amount : " + walletRequest.getAmount() + ", Added to Wallet");
		return response;
	}
	
	@DeleteMapping("/delete/{mobile}")
	public Response deleteWallet(@PathVariable String mobile) throws Exception {
		System.out.println(mobile);
		boolean flag =  this.wService.deleteWallet(mobile);

		//Create Response
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Wallet Deleted Successfully");
		response.setData("Wallet with Mobile Number : " + mobile);
		return response;
	}
}
