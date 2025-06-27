package com.animal.api.admin.member.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.admin.member.mapper.AdminPointTypeMapper;
import com.animal.api.admin.member.model.request.AdminPointTypeUpdateRequestDTO;
import com.animal.api.auth.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class AdminPointTypeServiceImple implements AdminPointTypeService {

    private final AdminPointTypeMapper adminPointTypeMapper;

    @Override
    public void updatePointTypePoint(AdminPointTypeUpdateRequestDTO dto) {
        int result = adminPointTypeMapper.updatePointTypePoint(dto);
        if (result == 0) {
            throw new CustomException(404, "해당 포인트 타입이 존재하지 않습니다.");
        }
    }
}
