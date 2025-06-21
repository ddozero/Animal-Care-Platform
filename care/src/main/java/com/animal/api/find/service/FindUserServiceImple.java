package com.animal.api.find.service;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.auth.model.vo.UserVO;
import com.animal.api.common.service.CaptchaService;
import com.animal.api.email.mapper.CertificationMapper;
import com.animal.api.email.model.vo.CertificationVO;
import com.animal.api.find.mapper.FindMapper;
import com.animal.api.find.model.request.FindUserPasswordResetRequestDTO;
import com.animal.api.find.model.request.FindUserPasswordVerifyRequestDTO;
import com.animal.api.find.model.response.FindUserIdResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class FindUserServiceImple implements FindUserService {

	private final FindMapper findMapper;
	private final CertificationMapper certificationMapper;
	private final BCryptPasswordEncoder passwordEncoder;
	private final CaptchaService captchaService;
	
    /**
     * 일반 사용자 아이디 찾기
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
    /**
     * 일반 사용자 비밀번호 찾기 시 아이디 입력
     * @param userid
     */
    
    @Override
    public void initUserPasswordReset(String userid) {
        UserVO user = findMapper.findUserVOByUserid(userid);
        if (user == null) {
            throw new CustomException(404, "존재하지 않는 아이디입니다.");
        }
    }

    /**
     * 2단계: 비밀번호 재설정 인증
     * - 입력된 아이디의 유저 존재 여부 확인
     * - 이름, 이메일 정보와 유저 정보 일치 여부 확인
     * - 인증번호 유효성 및 일치 여부 확인
     */
    @Override
    public void verifyUserPasswordResetCode(FindUserPasswordVerifyRequestDTO dto) {
        UserVO user = findMapper.findUserVOByUserid(dto.getUserid());
        if (user == null) {
            throw new CustomException(404, "존재하지 않는 아이디입니다.");
        }

        // 이름 & 이메일 일치 확인
        if (!user.getName().equals(dto.getName()) || !user.getEmail().equalsIgnoreCase(dto.getEmail())) {
            throw new CustomException(400, "회원 정보가 일치하지 않습니다.");
        }

        CertificationVO cert = certificationMapper.findLatestValidByEmail(dto.getEmail());
        if (cert == null) {
            throw new CustomException(404, "인증 요청 정보가 없습니다.");
        }

        String requestCode = dto.getCode() != null ? dto.getCode().trim() : "";
        String savedCode = cert.getToken() != null ? cert.getToken().trim() : "";

        if (!requestCode.equals(savedCode)) {
            throw new CustomException(400, "유효하지 않거나 만료된 인증코드입니다.");
        }
        //인증 완료
        certificationMapper.markAsUsed(cert.getIdx()); 
	}

    /**
     * 3단계: 비밀번호 재설정 실행
     * - 사용자 상태 확인
     * - 새 비밀번호 형식 유효성 검사
     * - 비밀번호 암호화 후 저장
     */
    @Override
    public void resetUserPassword(FindUserPasswordResetRequestDTO dto) {
		// 1. 사용자 조회
		UserVO user = findMapper.findUserVOByUserid(dto.getUserid());
		if (user == null) {
			throw new CustomException(404, "존재하지 않는 계정입니다.");
		}

		// 2. 상태 체크
		if (user.getStatus() == -1) {
			throw new CustomException(403, "탈퇴 하거나 사용할 수 없는 계정입니다.");
		}
		if (user.getStatus() == 0) {
			throw new CustomException(403, "잠긴 계정입니다. 관리자에게 문의해주세요.");
		}
        //  캡차 검증
        if (!captchaService.verify(dto.getCaptcha())) {
            throw new CustomException(400, "캡차 검증에 실패했습니다.");
        }

        // 3. 비밀번호 유효성 검사
        String newPassword = dto.getNewPassword();
        String confirmPassword = dto.getConfirmPassword();
        
		// 4. 비밀번호 암호화 후 업데이트
		String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
		findMapper.updateUserPassword(user.getIdx(), encodedPassword);
	}
}
