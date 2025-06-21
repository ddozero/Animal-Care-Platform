package com.animal.api.find.service;

import com.animal.api.find.model.response.FindUserIdResponseDTO;

public interface FindService {

	FindUserIdResponseDTO findUserId(String name, String email);
}
