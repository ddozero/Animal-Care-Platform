package com.animal.api.index.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.index.notice.model.response.MainNoticeDTO;
import com.animal.api.index.notice.service.MainNoticeService;

@RestController
@RequestMapping("/api/main/notices")
public class MainNoticeController {
	
	private final MainNoticeService mainNoticeService;
	
	@Autowired
	public MainNoticeController(MainNoticeService mainNoticeService) {
		this.mainNoticeService = mainNoticeService;
	}

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentNotices() {
        List<MainNoticeDTO> notices = mainNoticeService.getRecentNotices();
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<List<MainNoticeDTO>>(200, "공지사항 조회 성공", notices));
    }
	
}
