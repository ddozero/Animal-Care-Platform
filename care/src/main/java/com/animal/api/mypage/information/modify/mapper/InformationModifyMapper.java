package com.animal.api.mypage.information.modify.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InformationModifyMapper {
	//내 정보 수정 1단계: 비밀번호 확인
	void passwordCheck(@Param("userid") String userid, @Param("password") String password);
}
