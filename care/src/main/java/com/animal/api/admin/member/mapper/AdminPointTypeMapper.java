package com.animal.api.admin.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.admin.member.model.request.AdminPointTypeUpdateRequestDTO;

@Mapper
public interface AdminPointTypeMapper {
    int updatePointTypePoint(AdminPointTypeUpdateRequestDTO dto);
}
