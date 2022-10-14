package com.applyloan.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.apply.loan.client.AuthClient;
import com.apply.loan.controller.ApplyLoanController;
import com.apply.loan.dto.ApplyLoanDTO;
import com.apply.loan.service.ApplyLoanServiceImpl;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ApplyLoanControllerTests {

	@InjectMocks
	ApplyLoanController applyLoanController;

	@Mock
	AuthClient authClient;

	@Mock
	ApplyLoanServiceImpl applyLoanServiceImpl;

//	@Mock
//	Config config;

	@Test
	@DisplayName("Checking loan details method with invalid token")
	void testLoanDetails_InValidToken() {

		ApplyLoanDTO applyLoanDTO = new ApplyLoanDTO();

		Mockito.when(authClient.validateToken(Mockito.any())).thenReturn("abcd test");

		String actual = applyLoanController.loanDetails(applyLoanDTO, "test");
		String expect = "Token is either expired or invalid...";

		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("Checking loan details method with valid token")
	void testLoanDetails_ValidToken() {

		ApplyLoanDTO applyLoanDTO = new ApplyLoanDTO();

		Mockito.when(authClient.validateToken(Mockito.any())).thenReturn("abcd true");
		Mockito.when(applyLoanServiceImpl.loanDetails(Mockito.any())).thenReturn("Mock Object");

		String actual = applyLoanController.loanDetails(applyLoanDTO, "test");
		String expect = "Mock Object";

		assertEquals(actual, expect);
	}
}
