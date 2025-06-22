package com.animal.api.find.shelter.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.find.model.response.FindUserIdResponseDTO;
import com.animal.api.find.shelter.model.request.FindShelterIdRequestDTO;
import com.animal.api.find.shelter.service.FindShelterService;

import lombok.RequiredArgsConstructor;
/**
 * 보호시설 사용자 아이디 찾기 컨트롤러
 * @author Whistler95
 * @since 2025-06-22
 */
@RestController
@RequestMapping("/api/find/shelter")
@RequiredArgsConstructor
public class FindShelterIdController {

    private final FindShelterService findShelterService;

    /**
     * 보호소 사용자 아이디 찾기
     * @param dto 이름 + 이메일 + 인증코드
     * @return 아이디, 가입일자
     */
    @PostMapping("/id")
    public ResponseEntity<?> findShelterId(@RequestBody @Valid FindShelterIdRequestDTO  dto) {
        FindUserIdResponseDTO result = findShelterService.findShelterId(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "아이디 찾기 성공", result));
    }
}