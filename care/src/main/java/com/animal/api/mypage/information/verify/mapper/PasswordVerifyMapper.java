package com.animal.api.mypage.information.verify.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PasswordVerifyMapper {

	String selectEncryptedPassword(@Param("userIdx") int userIdx);
}
