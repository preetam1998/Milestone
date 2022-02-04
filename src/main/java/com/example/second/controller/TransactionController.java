package com.example.second.controller;

import com.example.second.dto.Response;
import com.example.second.dto.TransactionRequestDto;
import com.example.second.models.Transaction;
import com.example.second.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("transaction")
public class TransactionController {
	
	
	@Autowired
	private TransactionService transactionService;
	
	
	@PostMapping("/transfer")
	public Response sendMoney(@RequestBody TransactionRequestDto transaction)
	{

		Transaction transactionResponse = this.transactionService.sendMoney(transaction.getPayerMobile(), transaction.getPayeeMobile(), transaction.getAmount());
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Transaction successfully Done");
		response.setData(transactionResponse.toString());
		return response;
	}
	@GetMapping("/transaction-history/{mobileNumber}")
	public Response getTransactionHistory(@PathVariable String mobileNumber)
	{
		Iterable<Transaction> transactionResponse = this.transactionService.getTransactionHistory(mobileNumber);
		Response response = new Response();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Transaction History ::");
		response.setData(transactionResponse.toString());
		return response;
	}

}
