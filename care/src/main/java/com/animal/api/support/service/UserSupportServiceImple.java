package com.animal.api.support.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.common.util.FileManager;
import com.animal.api.support.mapper.UserSupportMapper;
import com.animal.api.support.model.request.SearchNoticeRequestDTO;
import com.animal.api.support.model.response.UserNoticeResponseDTO;

@Service
@Primary
public class UserSupportServiceImple implements UserSupportService {

	@Autowired
	private UserSupportMapper mapper;

	@Autowired
	private FileManager fileManager;

	private int listSize = 10;
	private int pageSize = 5;

	@Override
	public List<UserNoticeResponseDTO> getAllNotice(int cp) {
		if (cp == 0) {
			cp = 1;
		} else {
			cp = (cp - 1) * listSize;
		}

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp); 

		List<UserNoticeResponseDTO> noticeLists = mapper.getAllNotice(map);

		return noticeLists;
	}

	@Override
	public PageInformationDTO getAllNoticePage(int cp) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getAllNoticeTotalCnt();

		PageInformationDTO page = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return page;
	}

	@Override
	public UserNoticeResponseDTO getNoticeDetail(int idx) {

		UserNoticeResponseDTO dto = mapper.getNoticeDetail(idx);
		if (dto == null) {
			return null;
		}

		dto.setFilePaths(fileManager.getFilePath("boards", idx));

		if (dto != null && dto.getContent() != null) {
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		}
		return dto;
	}

	@Override
	public int addNoticeViewCount(int idx) { // 게시판 조회수
		int count = mapper.updateNoticeViews(idx);
		return count;
	}

	@Override
	public List<UserNoticeResponseDTO> searchAllNotice(int cp, String title, String content) {

		SearchNoticeRequestDTO dto = new SearchNoticeRequestDTO(cp, listSize, title, content);
		List<UserNoticeResponseDTO> searchNoticeList = mapper.searchAllNotice(dto);

		return searchNoticeList;
	}

	@Override
	public PageInformationDTO searchNoticePage(int cp, String title, String content) {

		SearchNoticeRequestDTO dto = new SearchNoticeRequestDTO(cp, listSize, title, content);
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getSearchNoticeTotalCnt(dto);

		PageInformationDTO page = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return page;
	}

}