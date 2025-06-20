package com.animal.api.support.service;

import java.util.List;

import com.animal.api.support.model.response.UserNoticeResponseDTO;

public interface UserSupportService {

	public List<UserNoticeResponseDTO> getAllNotice(int listSize, int cp);

	public UserNoticeResponseDTO getNoticeDetail(int idx);

	public List<UserNoticeResponseDTO> searchAllNotice(int listSize, int cp, String title, String content);

	public int addNoticeViewCount(int idx);

}
