package com.animal.api.admin.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.admin.board.mapper.AdminBoardMapper;
import com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO;

@Service
@Primary
public class AdminBoardServiceImple implements AdminBoardService {

	@Autowired
	private AdminBoardMapper mapper;

	@Override
	public int updateNotice(NoticeUpdateRequestDTO dto, int idx) {
		Integer boardTypeIdx = mapper.checkBoardType(idx);
		
		if(boardTypeIdx==null) {
			return NOTICE_NOT_FOUND;
		}

		if (boardTypeIdx != 1) { // 공지사항인지 확인
			return NOT_NOTICE;
		}

		dto.setIdx(idx);
		int result = mapper.updateNotice(dto);

		result = result > 0 ? UPDATE_SUCCESS : ERROR;
		return result;
	}

	@Override
	public int deleteNotice(int idx) {
		Integer boardTypeIdx = mapper.checkBoardType(idx);

		if (boardTypeIdx == null) {
			return NOTICE_NOT_FOUND;
		}
		
		if (boardTypeIdx != 1) { // 공지사항인지 확인
			return NOT_NOTICE;
		}

		int result = mapper.deleteNotice(idx);

		result = result > 0 ? DELETE_SUCCESS : ERROR;
		return result;
	}
}
