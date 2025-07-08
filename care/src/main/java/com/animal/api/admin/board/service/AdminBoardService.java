package com.animal.api.admin.board.service;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.admin.board.model.request.NoticeInsertRequestDTO;
import com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO;

public interface AdminBoardService {
	public static int POST_SUCCESS = 1;
	public static int UPDATE_SUCCESS = 2;
	public static int DELETE_SUCCESS = 3;
	public static int UPLOAD_SUCCESS = 4;
	public static int NOT_NOTICE = 5;
	public static int NOTICE_NOT_FOUND = 6;
	public static int NOT_NOMAL_BOARD = 7;
	public static int NOMAL_BOARD_NOT_FOUND = 8;
	public static int ERROR = -1;

	public int deleteBoard(int idx);

	public int updateNotice(NoticeUpdateRequestDTO dto, int idx);

	public int deleteNotice(int idx);

	public int insertNotice(NoticeInsertRequestDTO dto, int userIdx);

	public int uploadNoticeFiles(MultipartFile[] files, int idx);
}
