package com.example.second.controller;

import com.example.second.dto.Response;
import com.example.second.dto.TransactionRequestDto;
import com.example.second.models.Transaction;
import com.example.second.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("transaction")
public class TransactionController {
	
	
	@Autowired
	private TransactionService transactionService;

	// Get Logger for the Class
	private static Logger logger = LogManager.getLogger(TransactionController.class);


	@PostMapping("/transfer")
	public Response sendMoney(@RequestBody TransactionRequestDto transaction)
	{
		logger.info("Controller : API call for Transaction Received by Controller");
		logger.warn("Controller : Transaction :::" + transaction.toString());

		Transaction transactionResponse = this.transactionService.sendMoney(transaction.getPayerMobile(), transaction.getPayeeMobile(), transaction.getAmount());

		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Transaction successfully Done");
		response.setData(transactionResponse.toString());
		logger.debug("Controller : Transaction Status : " + transactionResponse.getStatus() + " For Wallet : " + transactionResponse.getPayer());
		return response;
	}


	@GetMapping("/transaction-history/{mobileNumber}")
	public Response getTransactionHistory(@PathVariable String mobileNumber)
	{
		logger.info("Controller : API call for Transaction Received by Controller");
		logger.warn("Controller : History Fetched  !!!");
		Iterable<Transaction> transactionResponse = this.transactionService.getTransactionHistory(mobileNumber);


		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Transaction History ::");
		response.setData(transactionResponse.toString());
		logger.debug("Controller : Transaction History for Wallet : " + mobileNumber + "History Fetched : " + transactionResponse.toString());

		return response;
	}

}
