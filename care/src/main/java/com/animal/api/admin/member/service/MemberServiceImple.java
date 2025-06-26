package com.animal.api.admin.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.admin.member.mapper.MemberMapper;
import com.animal.api.admin.member.model.response.MemberListResponseDTO;
import com.animal.api.common.model.PageResult;

@Service
@Primary
public class MemberServiceImple implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public PageResult<MemberListResponseDTO> getMemberList(String keyword, String type, Integer userType,
			Integer status, int page, int size) {

		int offset = (page - 1) * size;
		
		List<MemberListResponseDTO> list = memberMapper.selectMember(keyword, type, userType, status, offset, size);
		
		int total = memberMapper.countMember(keyword, type, userType, status);
		
		return new PageResult<>(total, list);
	}
}
