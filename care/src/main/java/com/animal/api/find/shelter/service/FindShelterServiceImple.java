package com.animal.api.find.shelter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.auth.model.vo.UserVO;
import com.animal.api.common.service.CaptchaServiceTest;
import com.animal.api.email.mapper.CertificationMapper;
import com.animal.api.email.model.vo.CertificationVO;
import com.animal.api.find.model.response.FindUserIdResponseDTO;
import com.animal.api.find.shelter.mapper.ShelterFindMapper;
import com.animal.api.find.shelter.model.request.FindShelterIdRequestDTO;
import com.animal.api.find.shelter.model.request.FindShelterPasswordResetRequestDTO;
import com.animal.api.find.shelter.model.request.FindShelterPasswordVerifyRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class FindShelterServiceImple implements FindShelterService {

	private final ShelterFindMapper shelterFindMapper;
	private final CertificationMapper certificationMapper;
	private final BCryptPasswordEncoder passwordEncoder;
	private final CaptchaServiceTest captchaService; // 추후 프론트 개발 시 실제 캡챠랑 연동

	/**
	 * @Value("${google.recaptcha.secret}") 프론트 구현 단계 시 연결 예정 private String
	 * recaptchaSecretKey; 외부 설정으로 주입
	 */
    @Value("${google.recaptcha.secret}")
    private String recaptchaSecretKey; // ← 외부 설정으로 주입
    /**
     * 보호시설 사용자 아이디 찾기
     * 이메일 인증 코드 검증 후,
     * 이름 + 이메일이 일치하는 사용자 정보를 조회하여
     * 아이디와 가입일자를 반환한다.
     */
	@Override
	public FindUserIdResponseDTO findShelterId(FindShelterIdRequestDTO dto) {
        CertificationVO cert = certificationMapper.findLatestValidByEmail(dto.getEmail());

        if (cert == null) {
            throw new CustomException(404, "인증 요청 정보가 없습니다. 인증번호를 다시 요청해주세요.");
        }

        String requestCode = dto.getCode() != null ? dto.getCode().trim() : "";
        String savedCode = cert.getToken() != null ? cert.getToken().trim() : "";

        if (!requestCode.equals(savedCode)) {
            throw new CustomException(400, "유효하지 않거나 만료된 인증코드입니다.");
        }

        FindUserIdResponseDTO response = shelterFindMapper.findShelterByNameAndEmail(dto.getName(), dto.getEmail());

        if (response == null) {
            throw new CustomException(404, "일치하는 회원 정보를 찾을 수 없습니다.");
        }

        return response;
	}

	/**
	 * 비밀번호 찾기 1단계: Shelter 사용자의 아이디 검사
	 */
	@Override
	public void initShelterPasswordReset(String userid) {
		UserVO user = shelterFindMapper.findShelterVOByUserid(userid);

		if (user == null || user.getUserTypeIdx() != 2) {
			throw new CustomException(404, "일치하는 보호소 아이디가 없습니다");
		}
	}

	/**
	 * 2단계: 비밀번호 재설정 인증
	 */
	@Override
	public void verifyShelterPasswordResetCode(FindShelterPasswordVerifyRequestDTO dto) {
		UserVO user = shelterFindMapper.findShelterVOByUserid(dto.getUserid());

		if (user == null || user.getUserTypeIdx() != 2) {
			throw new CustomException(404, "등록되지 않은 보호소 계정입니다");
		}
		// 이름 & 이메일 일치 확인
		if (!user.getName().equals(dto.getName()) || !user.getEmail().equalsIgnoreCase(dto.getEmail())) {
			throw new CustomException(400, "회원 정보가 일치하지 않습니다");
		}

		CertificationVO cert = certificationMapper.findLatestValidByEmail(dto.getEmail());

		if (cert == null) {
			throw new CustomException(404, "인증 요청 정보가 없스니다");
		}

		String requestCode = dto.getCode() != null ? dto.getCode().trim() : "";
		String savedCode = cert.getToken() != null ? cert.getToken().trim() : "";

		if (!requestCode.equals(savedCode)) {
			throw new CustomException(400, "유호하지 않거나 만료된 인증코드 입니다");
		}
		//인증 완료 후 사용 처리
		certificationMapper.markAsUsed(cert.getIdx());
	}

    /**
     * 3단계: 비밀번호 재설정 실행
     * - 사용자 상태 확인
     * - 새 비밀번호 형식 유효성 검사
     * - 비밀번호 암호화 후 저장
     */
	@Override
	public void resetShelterPassword(FindShelterPasswordResetRequestDTO dto) {
		// 1. 사용자 조회
		UserVO user = shelterFindMapper.findShelterVOByUserid(dto.getUserid());
        if (user == null || user.getUserTypeIdx() != 2) {
            throw new CustomException(404, "일치하는 보호소 계정이 없습니다.");
        }
		// 2. 상태 체크
        if (user.getStatus() == -1) {
            throw new CustomException(403, "탈퇴 하거나 사용 가능성이 없는 계정입니다.");
        }
        if (user.getStatus() == 0) {
            throw new CustomException(403, "잠긴 계정입니다. 관리자에게 문의해주세요.");
        }

        if (!captchaService.verify(dto.getCaptcha())) {
            throw new CustomException(400, "캡처 인증에 실패했습니다.");
        }
        // 3. 비밀번호 유효성 검사
        String newPassword = dto.getNewPassword();
        String confirmPassword = dto.getConfirmPassword();

        if (newPassword == null || newPassword.length() < 9 || newPassword.length() > 20) {
            throw new CustomException(400, "비밀번호는 9자 이상 20자 이하로 입력해주세요.");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new CustomException(400, "비밀번호 확인이 일치하지 않습니다.");
        }
		// 4. 비밀번호 암호화 후 업데이트
        String encodedPassword = passwordEncoder.encode(newPassword);
        shelterFindMapper.updateShelterPassword(user.getIdx(), encodedPassword);
    }
}
