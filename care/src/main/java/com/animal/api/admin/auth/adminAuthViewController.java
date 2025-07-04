package com.animal.api.admin.auth;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.animal.api.auth.model.response.LoginResponseDTO;

@Controller
@RequestMapping("/admin")
public class adminAuthViewController {

	@GetMapping
	public String adminHome(HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return "/common/admin/adminLogin";
		}else {
			return "/admin/shelterJoinList";
		}
	}
	
	@GetMapping("/login")
	public String adminLogin(HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");
		if (loginAdmin == null) { // 로그인 여부 검증
			return "/common/admin/adminLogin";
		}else {
			return "/admin/shelterJoinList";
		}
	}
}
