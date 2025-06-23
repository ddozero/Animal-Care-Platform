package com.animal.api.board.mapper;

import java.util.List;
import java.util.Map;

import com.animal.api.board.model.response.AllBoardListResponseDTO;

public interface UserBoardMapper {
	public List<AllBoardListResponseDTO> getAllBoards(Map map);
}
