package com.animal.api.auth.service;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.auth.mapper.AuthMapper;
import com.animal.api.auth.model.request.LoginRequestDTO;
import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.auth.model.vo.ShelterVO;
import com.animal.api.auth.model.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class AuthServiceImple implements AuthService {

	private final AuthMapper authMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginResponseDTO login(LoginRequestDTO dto) {

		UserVO user = authMapper.findUserById(dto.getId());

		if (user == null) {
			throw new CustomException(404, "존재하지 않는 아이디입니다");
		}

		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new CustomException(401, "비밀번호가 일치하지 않습니다");
		}
		
		if(user.getStatus() == -1) {
			throw new CustomException(410, "탈퇴한 회원입니다.");
		}

		if(user.getUserTypeIdx() == 2 & user.getStatus() == 0) {
			throw new CustomException(403, "보호시설 계정은 관리자 승인 후 로그인할 수 있습니다.");
		}
		
		if(user.getUserTypeIdx() == 3) {
			throw new CustomException(403, "관리자는 별도의 로그인 경로를 이용해주세요.");
		}
		
		authMapper.updateLastLoginAt(user.getIdx());

		LoginResponseDTO res = new LoginResponseDTO();

		// USERS 테이블 기본 정보 포함
		res.setIdx(user.getIdx());
		res.setUserTypeIdx(user.getUserTypeIdx());
		res.setUserTypeName(user.getUserTypeName());

		res.setId(user.getId());
		res.setEmail(user.getEmail());
		res.setName(user.getName());
		res.setNickname(user.getNickname());
		res.setBirthDate(user.getBirthDate());
		res.setGender(user.getGender());
		res.setTel(user.getTel());
		res.setZipCode(user.getZipCode());
		res.setAddress(user.getAddress());
		res.setAddressDetail(user.getAddressDetail());
		res.setPoint(user.getPoint());
		res.setCreatedAt(user.getCreatedAt());
		res.setUpdatedAt(user.getUpdatedAt());
		res.setLastLoginAt(user.getLastLoginAt());
		res.setWithdrawnAt(user.getWithdrawnAt());
		res.setStatus(user.getStatus());
		res.setLocked(user.getLocked());
		res.setLockCount(user.getLockCount());
		res.setLockedAt(user.getLockedAt());

		// 보호소 회원인 경우 추가 정보 조회
		if (user.getUserTypeIdx() == 2) {
			ShelterVO shelter = authMapper.findShelterByUserIdx(user.getIdx());

			res.setShelterTypeIdx(shelter.getShelterTypeIdx());
			res.setShelterTypeName(shelter.getShelterTypeName());
			res.setShelterTel(shelter.getShelterTel());
			res.setShelterName(shelter.getShelterName());
			res.setShelterPersonName(shelter.getShelterPersonName());
			res.setShelterZipCode(shelter.getShelterZipCode());
			res.setShelterAddress(shelter.getShelterAddress());
			res.setShelterAddressDetail(shelter.getShelterAddressDetail());
			res.setShelterEmail(shelter.getShelterEmail());
			res.setShelterDescription(shelter.getShelterDescription());
			res.setShelterBusinessNumber(shelter.getShelterBusinessNumber());
			res.setShelterBusinessFile(shelter.getShelterBusinessFile());
		}

		return res;
		
	}
	
	//관리자 게정 로그인 로직
	@Override
	public LoginResponseDTO loginAdmin(LoginRequestDTO dto) {
		
		// 1.관리자계정 존재 여부 확인 
		UserVO user = authMapper.findUserById(dto.getId());
		
		if(user == null ) {
			throw new CustomException(404, "존재하지 않는 아이디 입니다");
		}
		
	    // 2. 비밀번호 일치 여부
	    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
	        throw new CustomException(401, "비밀번호가 일치하지 않습니다.");
	    }

	    // 3. 관리자 권한 확인
	    if (user.getUserTypeIdx() != 3 ) {
	        throw new CustomException(403, "관리자 권한이 없습니다.");
	    }

	    // 4. 관리자 계정 상태 확인 (선택적으로 잠금, 탈퇴 등 처리 가능)
	    if (user.getStatus() == -1 ) {
	        throw new CustomException(410, "탈퇴한 관리자 계정입니다.");
	    }
	    if (user.getLocked() == 1 ) {
	        throw new CustomException(423, "잠긴 관리자 계정입니다.");
	    }

	    // 5. 마지막 로그인 시간 업데이트
	    authMapper.updateLastLoginAt(user.getIdx());

	    // 6. 관리자 정보만 담아서 리턴 
	    LoginResponseDTO admin = new LoginResponseDTO();
	    admin.setIdx(user.getIdx());
	    admin.setUserTypeIdx(user.getUserTypeIdx());
	    admin.setUserTypeName(user.getUserTypeName());
	    admin.setId(user.getId());
	    admin.setEmail(user.getEmail());
	    admin.setName(user.getName());

	    return admin;
	}
}
