package com.animal.api.admin.board.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminBoardMapper {

	public Integer checkBoardType(int idx);

	public int updateNotice(int idx);

}
