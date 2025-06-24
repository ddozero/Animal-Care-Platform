package com.animal.api.admin.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.admin.board.mapper.AdminBoardMapper;

@Service
@Primary
public class AdminBoardServiceImple implements AdminBoardService {
	
	@Autowired
	private AdminBoardMapper mapper;
}
