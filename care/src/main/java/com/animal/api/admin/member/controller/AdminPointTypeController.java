package com.animal.api.admin.member.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.admin.member.model.request.AdminPointTypeUpdateRequestDTO;
import com.animal.api.admin.member.service.AdminPointTypeService;
import com.animal.api.common.model.OkResponseDTO;

import lombok.RequiredArgsConstructor;

/**
 * [관리자 전용] 포인트 타입 관리 컨트롤러
 * 
 * 포인트 타입별 고정 포인트 금액(예: 봉사 후기 작성 시 500포인트)을 수정할 수 있는 관리자 API를 제공합니다
 * 
 * @author Whistler95
 * @since 2025-06-27
 */
@RestController
@RequestMapping("/api/admin/point-types")
@RequiredArgsConstructor
public class AdminPointTypeController {

    private final AdminPointTypeService adminPointTypeService;

    /**
     * [관리자 전용] 포인트 타입 금액 수정 API
     * 
     * 봉사 후기 작성, 입양 후기 작성 등의 포인트 타입에 대해 고정 포인트 금액을 수정합니다
     * 
     * @param dto 포인트 타입 IDX, 수정할 포인트 금액 정보
     * @return 성공 메시지
     */
    @PutMapping("/update")
    public ResponseEntity<?> updatePointType(@RequestBody @Valid AdminPointTypeUpdateRequestDTO dto) {
        adminPointTypeService.updatePointTypePoint(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "포인트 타입 금액 수정 완료", null));
    }
}
