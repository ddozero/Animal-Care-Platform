package com.animal.api.support.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.support.dao.UserSupportMapper;
import com.animal.api.support.model.response.UserNoticeResponseDTO;

@Service
@Primary
public class UserSupportServiceImple implements UserSupportService {
	
	@Autowired
	private UserSupportMapper mapper;
	
	@Override
	public List<UserNoticeResponseDTO> getAllNotice() throws Exception {
		List<UserNoticeResponseDTO> lists = mapper.getAllNotice();
		return lists;
	}
	

}
