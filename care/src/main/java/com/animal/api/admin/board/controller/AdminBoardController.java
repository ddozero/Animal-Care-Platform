package com.animal.api.admin.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.admin.board.service.AdminBoardService;

@RestController
@RequestMapping("/api/admin/boards")
public class AdminBoardController {

	@Autowired
	private AdminBoardService service;
}
