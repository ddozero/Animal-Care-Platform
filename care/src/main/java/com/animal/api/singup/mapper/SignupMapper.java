package com.animal.api.singup.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.auth.model.vo.UserVO;

@Mapper
public interface SignupMapper {

    int isDuplicateId(String id);

    int isDuplicateEmail(String email);

    int isDuplicateNickname(String nickname);

    int insertUser(UserVO user);
}
