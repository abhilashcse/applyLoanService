package com.apply.loan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ApplyLoanModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer loanId;
	private String userName;
	private String loanType;
	private String loanAmount;
	private String date;
	private String rateOfInterest;
	private String durationOfLoan;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setRateOfInterest(String rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public void setDurationOfLoan(String durationOfLoan) {
		this.durationOfLoan = durationOfLoan;
	}

}
