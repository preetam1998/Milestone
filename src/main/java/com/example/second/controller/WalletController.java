package com.example.second.controller;

import com.example.second.dto.Response;
import com.example.second.dto.WalletRequestDto;
import com.example.second.models.UserWallet;
import com.example.second.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wallet")
public class WalletController {
	
	@Autowired 
	private WalletService wService;

	@GetMapping("/fetch-wallet/{mobile}")
	public Response getWallet( @PathVariable String mobile) throws Exception {

		// check Total Balance on Wallet
		UserWallet wallet =  this.wService.getWallet(mobile);

		//Create Response
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Fetching  Wallet ");
		response.setData("Mobile : " + mobile + ", Amount : " + wallet.getAmount());
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
}
