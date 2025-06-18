package com.animal.api.support.service;

import java.util.List;
import com.animal.api.support.model.response.UserNoticeResponseDTO;

public interface UserSupportService {
	
	public List<UserNoticeResponseDTO> getAllNotice() throws Exception;

}
