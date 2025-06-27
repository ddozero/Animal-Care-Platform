package com.animal.api.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.board.model.request.BoardCommentUpdateRequestDTO;
import com.animal.api.board.model.request.BoardSearchRequestDTO;
import com.animal.api.board.model.request.BoardUpdateRequestDTO;
import com.animal.api.board.model.request.BoardWriteRequestDTO;
import com.animal.api.board.model.response.AllBoardCommentsResponseDTO;
import com.animal.api.board.model.response.AllBoardListResponseDTO;
import com.animal.api.board.model.response.BoardDetailResponseDTO;

@Mapper
public interface UserBoardMapper {
	public List<AllBoardListResponseDTO> getAllBoards(Map map);

	public List<AllBoardListResponseDTO> searchBoards(BoardSearchRequestDTO dto);

	public int getMaxRef();

	public int addBoard(BoardWriteRequestDTO dto);

	public BoardDetailResponseDTO getBoardDetail(int idx);

	public int updateBoardViews(int idx);

	public int updateBoard(BoardUpdateRequestDTO dto);

	public Integer checkMyBoard(int idx);

	public int deleteBoard(int idx);

	public Integer checkMyHeart(Map map);

	public int addBoardHeart(Map map);

	public int deleteBoardHeart(Map map);

	public Integer checkBoardExists(int idx);

	public List<AllBoardCommentsResponseDTO> getBoardComments(Map map);

	public int updateBoardComment(BoardCommentUpdateRequestDTO dto);
}
