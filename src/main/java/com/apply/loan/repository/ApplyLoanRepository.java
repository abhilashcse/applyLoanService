package com.apply.loan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apply.loan.model.ApplyLoanModel;

@Repository
public interface ApplyLoanRepository extends JpaRepository<ApplyLoanModel, String> {

	public Optional<ApplyLoanModel> findByUserName(String username); 
	
	public Optional<ApplyLoanModel> findByLoanType(String loantype); 

}
