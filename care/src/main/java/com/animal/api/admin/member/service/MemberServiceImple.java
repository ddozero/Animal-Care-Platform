package com.animal.api.admin.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animal.api.admin.member.mapper.MemberMapper;
import com.animal.api.admin.member.model.request.MemberUpdateRequestDTO;
import com.animal.api.admin.member.model.response.MemberDatailResopnseDTO;
import com.animal.api.admin.member.model.response.MemberListResponseDTO;
import com.animal.api.auth.exception.CustomException;
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

	@Override
	public MemberDatailResopnseDTO getMemberDetail(int userIdx) {

		MemberDatailResopnseDTO dto = memberMapper.selectMemberDetail(userIdx);
		
		if( dto == null ) {
			throw new CustomException(404, "존재하지 않는 회원입니다.");
		}
	
		return dto;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMember(MemberUpdateRequestDTO dto) {
		
		int memberUpdated = memberMapper.updateMember(dto);
		
		if(memberUpdated == 0) {
			throw new CustomException(404, "존재하지 않는 회원입니다.");
		}
		
	    // 1. 기존 유저 정보 가져오기
		MemberDatailResopnseDTO existing = memberMapper.selectMemberDetail(dto.getUserIdx());
		
	    // 2. 보호소 정보가 있다면 업데이트
		if(existing != null && existing.getUserType() == 2) {
			memberMapper.updateShelterMember(dto);
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteMember(int userIdx) {
	    int updated = memberMapper.deleteMember(userIdx);
	    if (updated == 0) {
	        throw new CustomException(404, "존재하지 않는 회원입니다.");
	    }
	}
}
