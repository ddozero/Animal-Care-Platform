package com.animal.api.find.model.request;

import lombok.Data;

@Data
public class FindUserIdRequestDTO {

	private String name;
	private String email;
	private String code;
	
}
