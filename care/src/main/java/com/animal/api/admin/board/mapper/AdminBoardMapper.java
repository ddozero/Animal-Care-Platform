package com.animal.api.admin.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO;

@Mapper
public interface AdminBoardMapper {

	public Integer checkBoardType(int idx);

	public int updateNotice(NoticeUpdateRequestDTO dto);

}
