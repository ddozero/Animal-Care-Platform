package com.animal.api.support.service;

import java.util.List;
import java.util.Map;
import com.animal.api.support.model.response.*;

public interface UserSupportService {
	
	public List<UserNoticeResponseDTO> getAllNotice(int listSize, int cp);
	public UserNoticeResponseDTO getNoticeDetail(int idx);
	public List<UserNoticeResponseDTO> searchAllNotice(String fkey, String fvalue);
	
}
