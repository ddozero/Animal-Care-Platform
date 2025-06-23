package com.animal.api.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.board.model.request.BoardSearchRequestDTO;
import com.animal.api.board.model.response.AllBoardListResponseDTO;

@Mapper
public interface UserBoardMapper {
	public List<AllBoardListResponseDTO> getAllBoards(Map map);

	public List<AllBoardListResponseDTO> searchBoards(BoardSearchRequestDTO dto);
}
