package com.animal.api.index.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.index.notice.model.response.MainNoticeDTO;

@Mapper
public interface MainNoticeMapper {

	List<MainNoticeDTO> selectRecentNotices();
}
