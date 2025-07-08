package com.animal.api.support.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.support.model.request.SearchNoticeRequestDTO;
import com.animal.api.support.model.response.*;

@Mapper
public interface UserSupportMapper {

	public List<UserNoticeResponseDTO> getAllNotice(Map map);
	
	public int getAllNoticeTotalCnt(); //페이징 구현을 위한 total count

	public UserNoticeResponseDTO getNoticeDetail(int idx);

	public List<UserNoticeResponseDTO> searchAllNotice(SearchNoticeRequestDTO dto);
	
	public int getSearchNoticeTotalCnt(SearchNoticeRequestDTO dto); //페이징 구현을 위한 total count

	public int updateNoticeViews(int idx);
	

}
