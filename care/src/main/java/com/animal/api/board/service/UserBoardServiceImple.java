package com.animal.api.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.board.mapper.UserBoardMapper;
import com.animal.api.board.model.request.BoardSearchRequestDTO;
import com.animal.api.board.model.response.AllBoardListResponseDTO;

@Service
@Primary
public class UserBoardServiceImple implements UserBoardService {

	@Autowired
	private UserBoardMapper mapper;

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
}
