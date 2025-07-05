package com.animal.api.index.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.index.notice.mapper.MainNoticeMapper;
import com.animal.api.index.notice.model.response.MainNoticeDTO;

@Service
@Primary
public class MainNoticeServiceimpl implements MainNoticeService {

	private final MainNoticeMapper mainNoticeMapper;
	
	@Autowired
	public MainNoticeServiceimpl (MainNoticeMapper mainNoticeMapper) {
		this.mainNoticeMapper = mainNoticeMapper;
	}
	
	@Override
	public List<MainNoticeDTO> getRecentNotices() {
		List<MainNoticeDTO> list = mainNoticeMapper.selectRecentNotices();
		return list;
	}

}
