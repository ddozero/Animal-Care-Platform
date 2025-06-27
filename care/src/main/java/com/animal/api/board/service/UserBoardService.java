package com.animal.api.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.board.model.request.BoardCommentRequestDTO;
import com.animal.api.board.model.request.BoardCommentUpdateRequestDTO;
import com.animal.api.board.model.request.BoardUpdateRequestDTO;
import com.animal.api.board.model.request.BoardWriteRequestDTO;
import com.animal.api.board.model.response.AllBoardCommentsResponseDTO;
import com.animal.api.board.model.response.AllBoardListResponseDTO;
import com.animal.api.board.model.response.BoardDetailResponseDTO;

public interface UserBoardService {
	static int GET_SUCCESS = 1;
	static int POST_SUCCESS = 2;
	static int IDX_NOT_FOUND = 3;
	static int TITLE_NOT_FOUND = 4;
	static int NICKNAME_NOT_FOUND = 5;
	static int CREATED_AT_NOT_FOUND = 6;
	static int VIEWS_NOT_FOUND = 7;
	static int LIKE_COUNT_NOT_FOUND = 8;
	static int REF_NOT_FOUND = 9;
	static int LEV_NOT_FOUND = 10;
	static int TURN_NOT_FOUND = 11;
	static int UPLOAD_SUCCESS = 12;
	static int NOT_OWNED_BOARD = 13;
	static int BOARD_NOT_FOUND = 14;
	static int DELETE_SUCCESS = 15;
	static int HEART_SUCCESS = 16;
	static int HEART_NOT_FOUND = 17;
	static int ALREADY_HEART = 18;
	static int COMMENT_NOT_FOUND = 19;
	static int NOT_MYCOMMENT = 20;
	static int UPDATE_SUCCESS = 21;
	static int ERROR = -1;

	public List<AllBoardListResponseDTO> getAllBoards(int listSize, int cp);

	public List<AllBoardListResponseDTO> searchBoards(String type, String keyword, int listSize, int cp);

	public int addBoard(BoardWriteRequestDTO dto);

	public int uploadBoardFile(MultipartFile[] files, int idx);

	public BoardDetailResponseDTO getBoardDetail(int idx);

	public int updateBoard(BoardUpdateRequestDTO dto, int idx);

	public int deleteBoard(int idx, int userIdx);

	public int checkMyHeart(int userIdx, int boardIdx);

	public int addBoardHeart(int userIdx, int boardIdx);

	public int deleteBoardHeart(int userIdx, int boardIdx);

	public Integer checkBoardExists(int idx);

	public List<AllBoardCommentsResponseDTO> getBoardComments(int boardIdx, int listSize, int cp);

	public int addBoardComment(BoardCommentRequestDTO dto, int idx, int boardCommentIdx, int userIdx);

	public int updateBoardComment(BoardCommentUpdateRequestDTO dto, int idx, int boardCommentIdx, int userIdx);
}
