package com.animal.api.mypage.information.modify.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.mypage.information.modify.model.request.InformationModifyRequsetDTO;
import com.animal.api.mypage.information.modify.model.response.InformationModifyResponseDTO;

@Mapper
public interface InformationModifyMapper {
	// IDX 로 유저 정보 조회
	InformationModifyResponseDTO selectUserInfo(@Param("userIdx") int userIdx);

	// 내 정보 수정
	int updateUserInfo(@Param("userIdx") int userIdx, @Param("dto") InformationModifyRequsetDTO dto);

	// 현재 비밀번호 확인
	String selectEncryptedPassword(@Param("userIdx") int userIdx); 
	
	// 내 비밀번호 변경
	int updatePassword(@Param("userIdx") int userIdx, @Param("encodedPassword") String encodedPassword);
	
	
	// 내 이메일 조회
	String selectUserEmail(@Param("userIdx") int userIdx);
	
	// 변경 이메일 중복 조회
	boolean existsByEmail(@Param("email") String email);
	
	// 내 이메일 변경
	int updateUserEmail(@Param("userIdx") int userIdx, @Param("newEmail") String newEmail);
	
}
