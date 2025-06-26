package com.animal.api.admin.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.admin.member.model.response.MemberListResponseDTO;

@Mapper
public interface MemberMapper {

	List<MemberListResponseDTO> selectMember(@Param("keyword") String keyword,
											 @Param("type") String type,
											 @Param("userType") Integer userType,
											 @Param("status") Integer status,
											 @Param("offset") int offset,
											 @Param("size") int size);
	
	int countMember(@Param("keyword") String keyword,
					@Param("type") String type,
					@Param("userType") Integer userType,
					@Param("status") Integer status);
}
