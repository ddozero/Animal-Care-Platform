package com.animal.api.support.service;

import java.util.List;

import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.support.model.response.UserNoticeResponseDTO;

public interface UserSupportService {

	public List<UserNoticeResponseDTO> getAllNotice(int cp);
	
	public PageInformationDTO getAllNoticePage(int cp); //사용자 공지사항 페이징 구현

	public UserNoticeResponseDTO getNoticeDetail(int idx);

	public List<UserNoticeResponseDTO> searchAllNotice(int cp, String title);
	
	public PageInformationDTO searchNoticePage(int cp, String title); //사용자 공지사항 검색 페이징 구현
	
	public int addNoticeViewCount(int idx);

}
