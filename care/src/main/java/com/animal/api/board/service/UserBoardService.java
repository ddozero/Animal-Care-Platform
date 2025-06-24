package com.animal.api.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.board.model.request.BoardWriteRequestDTO;
import com.animal.api.board.model.response.AllBoardListResponseDTO;

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
	static int ERROR = -1;

	public List<AllBoardListResponseDTO> getAllBoards(int listSize, int cp);

	public List<AllBoardListResponseDTO> searchBoards(String type, String keyword, int listSize, int cp);

	public int addBoard(BoardWriteRequestDTO dto);

	public int uploadBoardFile(MultipartFile[] files, int idx);
}
