package com.apply.loan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.apply.loan.dto.JWTResponse;

@FeignClient(name = "customerRegistrationService", url = "http://localhost:8084/")
public interface AuthClient {

	@PostMapping("/validate")
	public String validateToken(@RequestBody JWTResponse response);

}
