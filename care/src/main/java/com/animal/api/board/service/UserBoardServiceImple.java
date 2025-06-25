package com.animal.api.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.board.mapper.UserBoardMapper;
import com.animal.api.board.model.request.BoardSearchRequestDTO;
import com.animal.api.board.model.request.BoardWriteRequestDTO;
import com.animal.api.board.model.response.AllBoardListResponseDTO;
import com.animal.api.board.model.response.BoardDetailResponseDTO;
import com.animal.api.common.util.FileManager;

@Service
@Primary
public class UserBoardServiceImple implements UserBoardService {

	@Autowired
	private UserBoardMapper mapper;

	@Autowired
	private FileManager fileManager;

	@Override
	public List<AllBoardListResponseDTO> getAllBoards(int listSize, int cp) {
		if (cp == 0) {
			cp = 1;
		}
		cp = (cp - 1) * listSize;

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		List<AllBoardListResponseDTO> boardList = mapper.getAllBoards(map);

		return boardList;
	}

	@Override
	public List<AllBoardListResponseDTO> searchBoards(String type, String keyword, int listSize, int cp) {
		if (cp == 0) {
			cp = 1;
		}
		cp = (cp - 1) * listSize;

		BoardSearchRequestDTO request = new BoardSearchRequestDTO(type, keyword, listSize, cp);
		List<AllBoardListResponseDTO> boardList = mapper.searchBoards(request);

		return boardList;
	}

	@Override
	public int addBoard(BoardWriteRequestDTO dto) {
		int ref = mapper.getMaxRef();

		dto.setRef(ref + 1);

		int result = mapper.addBoard(dto);

		if (result == 1) {
			return POST_SUCCESS;
		} else {
			return ERROR;
		}
	}

	@Override
	public int uploadBoardFile(MultipartFile[] files, int idx) {
		boolean result = fileManager.uploadFiles("boards", idx, files);

		if (result) {
			return UPLOAD_SUCCESS;

		} else {
			return ERROR;
		}
	}

	@Override
	public BoardDetailResponseDTO getBoardDetail(int idx) {
		int result = mapper.updateBoardViews(idx);

		if (result > 0) {
			BoardDetailResponseDTO boardDetail = mapper.getBoardDetail(idx);
			return boardDetail;
		} else {
			return null;
		}
	}

}
