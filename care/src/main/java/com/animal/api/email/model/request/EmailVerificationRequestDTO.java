package com.animal.api.email.model.request;

import lombok.Data;

@Data
public class EmailVerificationRequestDTO {
	
	private String email;
	private String code;
	
}
