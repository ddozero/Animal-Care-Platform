package com.animal.api.support.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.support.mapper.UserSupportMapper;
import com.animal.api.support.model.response.UserNoticeResponseDTO;

@Service
@Primary
public class UserSupportServiceImple implements UserSupportService {

	@Autowired
	private UserSupportMapper mapper;

	@Override
	public List<UserNoticeResponseDTO> getAllNotice(int listSize, int cp){
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<UserNoticeResponseDTO> noticeLists = mapper.getAllNotice(map);
		
		return noticeLists;
	}
	
	@Override
	public UserNoticeResponseDTO getNoticeDetail(int idx) {
		UserNoticeResponseDTO dto = mapper.getNoticeDetail(idx);
		if(dto!=null&&dto.getContent()!=null){
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		}
		
		return dto;
	}
	
	@Override
	public List<UserNoticeResponseDTO> searchAllNotice(String fkey, String fvalue) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("fkey", fkey);
		map.put("fvalue", fvalue);
		
		List<UserNoticeResponseDTO> searchNotice = mapper.searchAllNotice(map);
		return searchNotice;
	}

}
