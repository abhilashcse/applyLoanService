package com.apply.loan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ApplyLoanApplicationTests {
	
	@InjectMocks
	ApplyLoanApplication applyLoanApplication;

	@Test
	void contextLoads() {
		assertTrue(true);
	}
	
	@Test
	void testCustomerRegistrationApplication() {
		assertThat(applyLoanApplication).isNotNull();
	}

}
