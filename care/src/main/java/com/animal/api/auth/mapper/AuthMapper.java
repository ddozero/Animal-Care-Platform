package com.animal.api.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.auth.model.vo.ShelterVO;
import com.animal.api.auth.model.vo.UserVO;

@Mapper
public interface AuthMapper {
	
	// 아이디로 사용자 조회 (유저, 보호소 공통)
	UserVO findUserById(String id);

    // 보호소 상세 정보 조회 (보호소 전용)
	ShelterVO findShelterByUserIdx(int idx);

	// 로그인 성공 시 마지막 로그인 시간 갱신
	void updateLastLoginAt(int idx);
	
	// 이메일로 사용자 조회 
    UserVO findByEmail(@Param("email") String email);
}
