package com.animal.api.find.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.auth.model.vo.UserVO;
import com.animal.api.find.model.response.FindUserIdResponseDTO;

@Mapper
public interface FindMapper {

	//아이디 찾기: 이름 + 이메일 → ID + 가입일자
	FindUserIdResponseDTO findUserByNameAndEmail(@Param("name") String name,@Param("email") String email);
	
	//비밀번호 재설정: 사용자 ID 기준 조회 (모든 단계 공통으로 사용)	
	UserVO findUserVOByUserid(@Param("userid") String userid);

	//비밀번호 재설정 최종 단계: 비밀번호 업데이트
	void updateUserPassword(@Param("userIdx") int userIdx, @Param("password") String password);

}
