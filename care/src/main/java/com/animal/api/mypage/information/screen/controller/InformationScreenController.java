package com.animal.api.mypage.information.screen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.mypage.information.screen.model.response.InformationScreenResponseDTO;
import com.animal.api.mypage.information.screen.service.InformationScreenService;

import lombok.RequiredArgsConstructor;

/**
 * 마이페이지 첫 화면 조회 컨트롤러
 * @author Whistler95
 * @since 2025-06-23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/screen")
public class InformationScreenController {

    private final InformationScreenService informationScreenService;

    /**
     * 마이페이지 첫 화면 - 내 활동 요약 정보 조회
     * @param request 로그인한 유저 정보 
     * @return 요약 정보 
     */
    @GetMapping("/info")
    public ResponseEntity<?> getMypageInfo(HttpServletRequest request) {
        InformationScreenResponseDTO info = informationScreenService.getInformationScreen(request);
        return ResponseEntity.ok(new OkResponseDTO<>(200, "마이페이지 정보 조회 성공", info));
    }
}
