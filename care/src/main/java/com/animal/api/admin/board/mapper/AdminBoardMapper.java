package com.animal.api.admin.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.admin.board.model.request.NoticeInsertRequestDTO;
import com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO;

@Mapper
public interface AdminBoardMapper {

	public Integer checkBoardType(int idx);

	public int deleteBoard(int idx);

	public int updateNotice(NoticeUpdateRequestDTO dto);

	public int deleteNotice(int idx);

	public int insertNotice(NoticeInsertRequestDTO dto);

}
