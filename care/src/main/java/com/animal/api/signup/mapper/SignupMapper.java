package com.animal.api.signup.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.auth.model.vo.ShelterVO;
import com.animal.api.auth.model.vo.UserVO;

public interface SignupMapper {

    int isDuplicateId(String id);

    int isDuplicateEmail(String email);

    int isDuplicateNickname(String nickname);

    int insertUser(UserVO user);
    
    int insertShelter(ShelterVO shelter);
}
