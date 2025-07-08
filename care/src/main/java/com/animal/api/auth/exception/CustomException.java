package com.animal.api.auth.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final int statusCode;
	private final Object data;

	public CustomException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
		this.data = null;
	}
	
	// data까지 넘길 수 있는 생성자
    public CustomException(int statusCode, String message, Object data) {
        super(message);
        this.statusCode = statusCode;
        this.data = data;
    }
}
