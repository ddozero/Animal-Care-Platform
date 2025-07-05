package com.animal.api.index.notice.service;

import java.util.List;

import com.animal.api.index.notice.model.response.MainNoticeDTO;

public interface MainNoticeService {

	List<MainNoticeDTO> getRecentNotices();
}
