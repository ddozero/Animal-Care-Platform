package com.animal.api.mypage.information.modify.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.mypage.information.modify.model.response.InformationModifyResponseDTO;

@Mapper
public interface InformationModifyMapper {
	// IDX 로 유저 정보 조회 
	InformationModifyResponseDTO selectUserInfo(@Param("userIdx") int userIdx);
}
