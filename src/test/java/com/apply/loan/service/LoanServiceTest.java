package com.apply.loan.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.apply.loan.dto.ApplyLoanDTO;
import com.apply.loan.exceptions.ApplicationAlreadyExistException;
import com.apply.loan.model.ApplyLoanModel;
import com.apply.loan.repository.ApplyLoanRepository;
import com.google.common.base.Optional;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class LoanServiceTest {

	@InjectMocks
	ApplyLoanServiceImpl applyLoanServiceImpl;

	@Mock
	ApplyLoanRepository applyLoanRepository;

//	public void test(String arg) {
//		assertTrue(true);
//	}

	@Test
	@DisplayName("Checking loan details method for empty response")
	void testLoanDetails_EmptyResponse() {
		ApplyLoanDTO applyLoanDTO = new ApplyLoanDTO();
		String actual = applyLoanServiceImpl.loanDetails(applyLoanDTO);

		String expect = "[{\"name\":\"Loan Type\",\"detail\":\"Loan Type is mandatory or missing\"},{\"name\":\"Loan Amount\",\"detail\":\"Loan Amount is mandatory or missing\"},{\"name\":\"Date\",\"detail\":\"Date is incorrect or missing\"},{\"name\":\"Rate Of Interest\",\"detail\":\"Rate Of Interest is incorrect or missing\"},{\"name\":\"Duration Of Loan\",\"detail\":\"Duration Of Loan is incorrect or missing\"}]";
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("Checking loan details method with non existing loan typer")
	void testLoanDetails_LoanType_NotPresent() {
		ApplyLoanDTO applyLoanDTO = new ApplyLoanDTO();

		applyLoanDTO.setLoanType("car");
		applyLoanDTO.setLoanAmount("200000");
		applyLoanDTO.setRateOfInterest("10");
		applyLoanDTO.setDurationOfLoan("24");
		applyLoanDTO.setDate("13-10-2022");
		applyLoanDTO.setUserName("abcd");

		ApplyLoanModel applyLoanModel = new ApplyLoanModel();
		applyLoanModel.setUserName("komal");

		java.util.Optional<ApplyLoanModel> optionalApplyLoanModel = java.util.Optional.of(applyLoanModel);

		Mockito.when(applyLoanRepository.findByUserName(Mockito.anyString())).thenReturn(optionalApplyLoanModel);

		String actual = applyLoanServiceImpl.loanDetails(applyLoanDTO);

		String expect = "{\"message\":\"Your application for loan has been successfully completed\"}";
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("Checking loan details method with non existing username")
	void testLoanDetails_User_NotPresent() {
		ApplyLoanDTO applyLoanDTO = new ApplyLoanDTO();

		applyLoanDTO.setLoanType("car");
		applyLoanDTO.setLoanAmount("200000");
		applyLoanDTO.setRateOfInterest("10");
		applyLoanDTO.setDurationOfLoan("24");
		applyLoanDTO.setDate("13-10-2022");
		applyLoanDTO.setUserName("abcd");

		ApplyLoanModel applyLoanModel = new ApplyLoanModel();

		applyLoanModel.setLoanType("personal");
		java.util.Optional<ApplyLoanModel> optionalApplyLoanModel = java.util.Optional.of(applyLoanModel);

		Mockito.when(applyLoanRepository.findByLoanType("car")).thenReturn(optionalApplyLoanModel);

		String actual = applyLoanServiceImpl.loanDetails(applyLoanDTO);

		String expect = "{\"message\":\"Your application for loan has been successfully completed\"}";
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("Checking loan details method with existing username and loan type ")
	void testLoanDetails_UserLoanType_Present() {
		ApplyLoanDTO applyLoanDTO = new ApplyLoanDTO();
		applyLoanDTO.setUserName("ajita");
		applyLoanDTO.setLoanType("personal");
		applyLoanDTO.setLoanAmount("200000");
		applyLoanDTO.setRateOfInterest("10");
		applyLoanDTO.setDurationOfLoan("24");
		applyLoanDTO.setDate("13-10-2022");

		ApplyLoanModel applyLoanModel = new ApplyLoanModel();
		applyLoanModel.setUserName("ajita");
		applyLoanModel.setLoanType("personal");
		java.util.Optional<ApplyLoanModel> optionalApplyLoanModel = java.util.Optional.of(applyLoanModel);

		Mockito.when(applyLoanRepository.findByUserName("ajita")).thenReturn(optionalApplyLoanModel);

		Mockito.when(applyLoanRepository.findByLoanType("personal")).thenReturn(optionalApplyLoanModel);

		String actual = applyLoanServiceImpl.loanDetails(applyLoanDTO);

		String expect = "{\"message\":\"Your application for this loan type is already in progress\"}";
		assertEquals(actual, expect);
	}

}
