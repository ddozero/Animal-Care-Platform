package com.animal.api.find.shelter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.auth.model.vo.UserVO;
import com.animal.api.find.model.response.FindUserIdResponseDTO;

@Mapper
public interface ShelterFindMapper {

    // 보호소 아이디 찾기
    FindUserIdResponseDTO findShelterByNameAndEmail(@Param("name") String name,@Param("email") String email);

    // 보호소 비밀번호 재설정 1단계: 아이디로 보호소 유저 조회
    UserVO findShelterVOByUserid(@Param("userid") String userid);

    // 보호소 비밀번호 재설정 3단계: 비밀번호 변경
    void updateShelterPassword(@Param("userIdx") int userIdx,@Param("password") String password);
}
