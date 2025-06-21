package com.animal.api.find.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.auth.model.vo.UserVO;
import com.animal.api.email.mapper.CertificationMapper;
import com.animal.api.email.model.vo.CertificationVO;
import com.animal.api.find.mapper.FindMapper;
import com.animal.api.find.model.request.FindUserIdRequestDTO;
import com.animal.api.find.model.response.FindUserIdResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class FindServiceImple implements FindService {

	private final FindMapper findMapper;
	private final CertificationMapper certificationMapper;
    /**
     * 이메일 인증 코드 검증 후,
     * 이름 + 이메일이 일치하는 사용자 정보를 조회하여
     * 아이디와 가입일자를 반환한다.
     */
    @Override
    public FindUserIdResponseDTO findUserId(String name, String email) {
        CertificationVO cert = certificationMapper.findLatestUsedByEmail(email);

        if (cert == null) {
            throw new CustomException(404, "인증 요청 정보가 없습니다. 인증번호를 다시 요청해주세요.");
        }

        FindUserIdResponseDTO response = findMapper.findUserByNameAndEmail(name, email);

        if (response == null) {
            throw new CustomException(404, "일치하는 회원 정보를 찾을 수 없습니다.");
        }

        return response;
    }
}
