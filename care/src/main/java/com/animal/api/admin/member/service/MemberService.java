package com.animal.api.admin.member.service;

import javax.naming.ldap.PagedResultsControl;

import com.animal.api.admin.member.model.response.MemberListResponseDTO;
import com.animal.api.common.model.PageResult;

public interface MemberService {

	PageResult<MemberListResponseDTO> getMemberList(String keyword, String type, Integer userType, Integer status, int page, int size);
}
