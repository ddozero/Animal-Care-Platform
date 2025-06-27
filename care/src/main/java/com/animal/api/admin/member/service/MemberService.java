package com.animal.api.admin.member.service;

import com.animal.api.admin.member.model.request.MemberUpdateRequestDTO;
import com.animal.api.admin.member.model.response.MemberDatailResopnseDTO;
import com.animal.api.admin.member.model.response.MemberListResponseDTO;
import com.animal.api.common.model.PageResult;

public interface MemberService {

	PageResult<MemberListResponseDTO> getMemberList(String keyword, String type, Integer userType, Integer status, int page, int size);
	
	MemberDatailResopnseDTO getMemberDetail(int userIdx);
	
	void updateMember(MemberUpdateRequestDTO dto);
}
