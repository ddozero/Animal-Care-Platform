package com.animal.api.find.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.email.service.EmailService;
import com.animal.api.find.mapper.FindMapper;
import com.animal.api.find.model.request.FindUserIdRequestDTO;
import com.animal.api.find.model.response.FindUserIdResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class FindServiceImple implements FindService {

	
	
	@Override
	public FindUserIdResponseDTO findUserId(FindUserIdRequestDTO dto) {

		return null;
	}
}
