package com.apply.loan.service;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.apply.loan.dto.ApplyLoanDTO;
import com.apply.loan.dto.ErrorResponse;
import com.apply.loan.dto.SuccessResponse;
import com.apply.loan.exceptions.ApplicationAlreadyExistException;
import com.apply.loan.model.ApplyLoanModel;
import com.apply.loan.repository.ApplyLoanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class ApplyLoanServiceImpl {
	
	@Autowired
	ApplyLoanRepository applyLoanRepository;

	public String loanDetails(ApplyLoanDTO applyLoanDTO) throws ApplicationAlreadyExistException{
	String response = null;

	List<ErrorResponse> listErrorResponse = validateLoanDetails(applyLoanDTO);
	if (!CollectionUtils.isEmpty(listErrorResponse)) {
		response = prepareErrorResponse(listErrorResponse);
		return response;
	}
	
	Optional<ApplyLoanModel> username = applyLoanRepository.findByUserName(applyLoanDTO.getUserName());
	Optional<ApplyLoanModel> loantype = applyLoanRepository.findByLoanType(applyLoanDTO.getLoanType());
//	log.info("loanType = "+loantype.get().getUserName());
	
	try {
		if(username.isPresent()) {
			if(loantype.isPresent()) {
				validateUsername();
			}
		}
	}
	catch(Exception e){
		return prepareSuccessResponse(e.getMessage());
	}
	//if (username.isPresent()) {
	//if(loantype.isPresent()) ;
		

	
	ApplyLoanModel applyLoanModel = transformIntoCustomerEntity(applyLoanDTO);
	applyLoanRepository.save(applyLoanModel);
	
	return prepareSuccessResponse("Your application for loan has been successfully completed");
}
	
private void validateUsername() {
	throw new ApplicationAlreadyExistException("Your application for this loan type is already in progress");
}

private ApplyLoanModel transformIntoCustomerEntity(ApplyLoanDTO applyLoanDTO) {
	
	ApplyLoanModel applyLoanModel = new ApplyLoanModel();
	
	applyLoanModel.setUserName(applyLoanDTO.getUserName());
	applyLoanModel.setLoanType(applyLoanDTO.getLoanType());
	applyLoanModel.setLoanAmount(applyLoanDTO.getLoanAmount());
	applyLoanModel.setDate(applyLoanDTO.getDate());
	applyLoanModel.setRateOfInterest(applyLoanDTO.getRateOfInterest());
	applyLoanModel.setDurationOfLoan(applyLoanDTO.getDurationOfLoan());
	

	return applyLoanModel;
}

private String prepareSuccessResponse(String message) {
	SuccessResponse successResponse = new SuccessResponse();

	successResponse.setMessage(message);
	ObjectMapper objectMapper = new ObjectMapper();
	String response = null;
	try {
		response = objectMapper.writeValueAsString(successResponse);
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return response;
}

private String prepareErrorResponse(List<ErrorResponse> listErrorResponse) {
	ObjectMapper objectMapper = new ObjectMapper();
	String response = null;
	try {
		response = objectMapper.writeValueAsString(listErrorResponse);
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return response;
}

private List<ErrorResponse> validateLoanDetails(ApplyLoanDTO applyLoanDTO) {

	List<ErrorResponse> listErrorResponse = new ArrayList<>();
	

	if (ObjectUtils.isEmpty(applyLoanDTO.getLoanType())) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setName("Loan Type");
		errorResponse.setDetail("Loan Type is mandatory or missing");
		listErrorResponse.add(errorResponse);
	}
	if (ObjectUtils.isEmpty(applyLoanDTO.getLoanAmount())) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setName("Loan Amount");
		errorResponse.setDetail("Loan Amount is mandatory or missing");
		listErrorResponse.add(errorResponse);
	}

	if (ObjectUtils.isEmpty(applyLoanDTO.getDate())) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setName("Date");
		errorResponse.setDetail("Date is incorrect or missing");

		listErrorResponse.add(errorResponse);
	}
	
	if (ObjectUtils.isEmpty(applyLoanDTO.getRateOfInterest())) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setName("Rate Of Interest");
		errorResponse.setDetail("Rate Of Interest is incorrect or missing");

		listErrorResponse.add(errorResponse);
	}
	
	if (ObjectUtils.isEmpty(applyLoanDTO.getDurationOfLoan())) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setName("Duration Of Loan");
		errorResponse.setDetail("Duration Of Loan is incorrect or missing");

		listErrorResponse.add(errorResponse);
	}

	return listErrorResponse;
}

}
