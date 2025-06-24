package com.animal.api.admin.board.service;

import com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO;

public interface AdminBoardService {
	public static int POST_SUCCESS = 1;
	public static int UPDATE_SUCCESS = 2;
	public static int DELETE_SUCCESS = 3;
	public static int UPLOAD_SUCCESS = 4;
	public static int NOT_NOTICE = 5;
	public static int ERROR = -1;

	public int updateNotice(NoticeUpdateRequestDTO dto, int idx);

	public int deleteNotice(int idx);
}
