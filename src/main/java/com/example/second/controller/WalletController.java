package com.example.second.controller;

import com.example.second.dto.Response;
import com.example.second.dto.WalletRequestDto;
import com.example.second.models.UserWallet;
import com.example.second.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wallet")
public class WalletController {
	
	@Autowired 
	private WalletService wService;

	// Get Logger for the Class
	private static Logger logger = LogManager.getLogger(WalletController.class);


	@GetMapping("/fetch-wallet")
	public Response getWallet( @RequestParam String mobileNumber) throws Exception
	{

		logger.info("Controller : API for fetching wallet reached to Controller");
		logger.warn("Controller : Fetching wallet ith mobile number : " + mobileNumber);
		// check Total Balance on Wallet
		UserWallet wallet =  this.wService.getWallet(mobileNumber);


		//Create Response
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Fetching  Wallet ");
		response.setData("Mobile : " + mobileNumber + ", Amount : " + wallet.getAmount());
		logger.debug("Controller :  Information  : " + response.getData());
		return response;
	}

	@PostMapping("/create-wallet")
	public Response createWallet(@RequestBody WalletRequestDto wallet) throws Exception
	{
		logger.info("Controller : API for creating wallet with mobile number : " + wallet.getMobileNumber());
		logger.warn("Controller : Creating wallet for mobile number : " + wallet.getMobileNumber());
		// Create Wallet
		UserWallet userWallet = this.wService.createWallet(wallet);


		//Create Response
		Response response = new Response();
		response.setStatus(HttpStatus.CREATED);
		response.setMessage("Wallet Created");
		response.setData(userWallet.toString());
		logger.debug("Controller :  Information : " + response.getData());

		return response;
	}
	
	@PutMapping("/add-balance/")
	public Response addAmtToWallet(@RequestParam String mobileNumber, @RequestBody WalletRequestDto walletRequest) throws Exception
	{
		logger.info("Controller : API for adding balance to  wallet with mobile number : " + mobileNumber);
		logger.warn("Controller : Adding  amount : " + walletRequest.getAmount()  +"rs  to wallet with mobile number : " + mobileNumber);

		boolean flag = this.wService.addMoneyToWallet(mobileNumber, walletRequest.getAmount());


		//Create Response
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Adding Money To Wallet");
		response.setData("Amount : " + walletRequest.getAmount() + ", Added to Wallet");
		logger.debug("Controller :  Information  : " + response.getData());
		return response;
	}
}
