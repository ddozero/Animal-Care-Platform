package com.animal.api.auth.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptTest {

	public static void main(String[] args) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoderPassword = encoder.encode("12345");
		
		System.out.println("BCrypt 암호화된 비밀번호: " + encoderPassword);
		
		
	}

}
