package com.animal.api.auth.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	
	private final int statusCode;

	public CustomException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}
}
