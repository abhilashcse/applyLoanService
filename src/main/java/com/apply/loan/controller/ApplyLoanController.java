package com.apply.loan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.apply.loan.client.AuthClient;
import com.apply.loan.dto.ApplyLoanDTO;
import com.apply.loan.dto.JWTResponse;
import com.apply.loan.exceptions.InvalidTokenException;
import com.apply.loan.repository.ApplyLoanRepository;
import com.apply.loan.service.ApplyLoanServiceImpl;

@RestController
@CrossOrigin
public class ApplyLoanController {
	private final Logger log = LoggerFactory.getLogger(ApplyLoanController.class);
	@Autowired
	ApplyLoanServiceImpl applyLoanServiceImpl;

	@Autowired
	ApplyLoanRepository applyLoanRepository;

	@Autowired
	AuthClient authClient;
	
	
	
	private String msg = "Token is either expired or invalid...";

	@PostMapping(value = "/applyLoan", produces = MediaType.APPLICATION_JSON_VALUE)
	public String loanDetails(@RequestBody ApplyLoanDTO applyLoanDTO,
			@RequestHeader(name = "Authorization", required = true) String token) throws InvalidTokenException {
		
		log.info("apply loan -started");
		JWTResponse response = new JWTResponse(token);
		
		String res = authClient.validateToken(response);
		String array[] = res.split(" ");
		//if (array[1].equals(config.getCheck()))
		if("true".equals(array[1])){
			return ( applyLoanServiceImpl.loanDetails(applyLoanDTO));
		}
		else {
		 return msg;
		}
	}
	
//	@PostMapping("/valid")
//	public String ValidateUser(@RequestHeader(name = "Authorization") String token) {
//		
//		JWTResponse response = new JWTResponse();
//		
//		String res = authClient.validateToken(response);
//		String array[] = res.split(" ");
//		if(array[1].equals("true")) {
//			return (array[0]);
//		}
//		else
//			return msg;
//		
//	}
}
