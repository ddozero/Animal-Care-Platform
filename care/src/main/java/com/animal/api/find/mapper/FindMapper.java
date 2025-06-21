package com.animal.api.find.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.find.model.response.FindUserIdResponseDTO;

@Mapper
public interface FindMapper {

	FindUserIdResponseDTO findUserByNameAndEmail(@Param("name") String name,@Param("email") String email);
}
