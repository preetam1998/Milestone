package com.example.second.controller;

import com.example.second.dto.Response;
import com.example.second.dto.TransactionRequestDto;
import com.example.second.enums.TransactionStatus;
import com.example.second.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.second.service.TransactionService;


@RestController
public class TransactionController {
	
	
	@Autowired
	private TransactionService transactionService;
	
	
	@PostMapping("transaction/transfer")
	public Response sendMoney(@RequestBody TransactionRequestDto transaction)
	{
		Response response = new Response();
		Transaction transactionResponse = this.transactionService.sendMoney(transaction.getPayerMobile(), transaction.getPayeeMobile(), transaction.getAmount());
		response.setStatus(HttpStatus.OK);
		response.setMessage("Transaction successfully Done");
		response.setData(transactionResponse.toString());
		return response;
	}


	
	@GetMapping("transaction/status/{id}")
	public Response getStatus(@PathVariable long id)
	{
		Response response = new Response();
		TransactionStatus transactionStatus = this.transactionService.getStatus(id);
		response.setStatus(HttpStatus.OK);
		response.setMessage("Fetch Status Of Transaction");
		response.setData(transactionStatus.toString());
		return response;
	}

}
