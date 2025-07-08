package com.animal.api.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards")
public class UserBoardViewController {

	@GetMapping
	public String boardList() {
		return "user/board/boardList";
	}

	@GetMapping("/{idx}")
	public String boardDetail(@PathVariable int idx) {
		return "user/board/boardDetail";
	}

	@GetMapping("/form")
	public String boardForm() {
		return "user/board/boardForm";
	}
}
