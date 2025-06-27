package com.animal.api.admin.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.admin.board.mapper.AdminBoardMapper;
import com.animal.api.admin.board.model.request.NoticeInsertRequestDTO;
import com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO;
import com.animal.api.common.util.FileManager;

@Service
@Primary
public class AdminBoardServiceImple implements AdminBoardService {

	@Autowired
	private AdminBoardMapper mapper;
	@Autowired
	private FileManager fileManager;

	@Override
	public int deleteBoard(int idx) {
		Integer boardTypeIdx = mapper.checkBoardType(idx);

		if (boardTypeIdx == null) {
			return NOMAL_BOARD_NOT_FOUND;
		}

		if (boardTypeIdx != 2) { // 공지사항인지 확인
			return NOT_NOMAL_BOARD;
		}
		int result = mapper.deleteBoard(idx);

		result = result > 0 ? DELETE_SUCCESS : ERROR;
		if (result == DELETE_SUCCESS) { // 게시글 삭제 시 파일도 같이 삭제
			fileManager.deleteFolder("boards", idx);
		}
		return result;
	}

	@Override
	public int updateNotice(NoticeUpdateRequestDTO dto, int idx) {
		Integer boardTypeIdx = mapper.checkBoardType(idx);

		if (boardTypeIdx == null) {
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
		if (result == DELETE_SUCCESS) { // 게시글 삭제 시 파일도 같이 삭제
			fileManager.deleteFolder("boards", idx);
		}
		return result;
	}

	@Override
	public int insertNotice(NoticeInsertRequestDTO dto, int userIdx) {
		dto.setUserIdx(userIdx);
		int result = mapper.insertNotice(dto);

		result = result > 0 ? POST_SUCCESS : ERROR;
		return result;
	}

	@Override
	public int uploadNoticeFiles(MultipartFile[] files, int idx) {
		boolean result = fileManager.uploadFiles("boards", idx, files);

		if (result) {
			return UPLOAD_SUCCESS;
		} else {
			mapper.deleteNotice(idx);
			return ERROR;
		}
	}
}
