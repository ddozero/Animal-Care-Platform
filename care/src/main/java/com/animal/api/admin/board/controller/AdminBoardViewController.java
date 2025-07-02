package com.animal.api.admin.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/notice")
public class AdminBoardViewController {

	@GetMapping
	public String noticeList() {
		return "admin/board/noticeList";
	}
	
	@GetMapping("/{idx}")
	public String noticeDetail() {
		return "admin/board/noticeDetail";
	}
	
	@GetMapping
	public String noticeAddForm() {
		return "admin/board/noticeAddForm";
	}
}
