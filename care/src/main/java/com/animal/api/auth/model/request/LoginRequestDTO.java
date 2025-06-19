package com.animal.api.auth.model.request;

import lombok.Data;

@Data
public class LoginRequestDTO {

	private String Id;
	private String password;
}
