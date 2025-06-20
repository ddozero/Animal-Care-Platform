package com.animal.api.signup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.auth.model.vo.ShelterVO;
import com.animal.api.auth.model.vo.UserVO;
import com.animal.api.signup.mapper.SignupMapper;
import com.animal.api.signup.model.request.ShelterSignupRequestDTO;
import com.animal.api.signup.model.request.UserSignupRequestDTO;

@Service
@Primary
public class SignupServiceImple implements SignupService {

	@Autowired
	private SignupMapper signupMapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	//일반 사용자 회원가입 Service
	@Override
	public void signupUser(UserSignupRequestDTO dto) {

		// 1. 중복 검사
		if (signupMapper.isDuplicateId(dto.getId()) > 0) {
			throw new CustomException(409, "이미 사용 중인 아이디입니다.");
		}

		if (signupMapper.isDuplicateEmail(dto.getEmail()) > 0) {
			throw new CustomException(409, "이미 가입된 이메일 입니다.");
		}

		if (signupMapper.isDuplicateNickname(dto.getNickname()) > 0) {
			throw new CustomException(409, "이미 사용 중인 닉네임입니다.");
		}

		// 2. DTO -> VO 매핑
		UserVO user = new UserVO();
		user.setUserTypeIdx(1); // 일반 사용자
		user.setId(dto.getId());
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword())); // 암호화
		user.setName(dto.getName());
		user.setNickname(dto.getNickname());
		user.setBirthDate(dto.getBirthDate());
		user.setGender(dto.getGender());
		user.setTel(dto.getTel());
		user.setZipCode(dto.getZipCode());
		user.setAddress(dto.getAddress());
		user.setAddressDetail(dto.getAddressDetail());

		// 3. DB 저장
		signupMapper.insertUser(user);
	}
	
	//보호시설 사용자 회원가입 Service
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void signupShelter(ShelterSignupRequestDTO dto) {

	    // 1. 중복 체크
	    if (signupMapper.isDuplicateId(dto.getId()) > 0) {
	        throw new CustomException(409, "이미 사용 중인 아이디입니다.");
	    }
	    if (signupMapper.isDuplicateEmail(dto.getEmail())> 0) {
	        throw new CustomException(409, "이미 사용 중인 이메일입니다.");
	    }
	    if (signupMapper.isDuplicateNickname(dto.getNickname()) > 0) {
	        throw new CustomException(409, "이미 사용 중인 닉네임입니다.");
	    }

	    // 2. USER VO 생성
	    UserVO user = new UserVO();
	    user.setUserTypeIdx(2); // 보호시설 고정
	    user.setId(dto.getId());
	    user.setEmail(dto.getEmail());
	    user.setPassword(passwordEncoder.encode(dto.getPassword()));
	    user.setName(dto.getName());
	    user.setNickname(dto.getNickname());
	    user.setBirthDate(dto.getBirthDate());
	    user.setGender(dto.getGender());
	    user.setTel(dto.getTel());
	    user.setZipCode(dto.getZipCode());
	    user.setAddress(dto.getAddress());
	    user.setAddressDetail(dto.getAddressDetail());
	    user.setStatus(0); // **관리자 승인 대기 상태

	    // 3. USERS 테이블에 insert
	    signupMapper.insertUser(user);

	    // 4. SHELTER VO 생성
	    ShelterVO shelter = new ShelterVO();
	    shelter.setUserIdx(user.getIdx()); // 방금 insert된 사용자 PK
	    shelter.setShelterTypeIdx(dto.getShelterTypeIdx());
	    shelter.setShelterTel(dto.getShelterTel());
	    shelter.setShelterName(dto.getShelterName());
	    shelter.setShelterPersonName(dto.getShelterPersonName());
	    shelter.setShelterZipCode(dto.getShelterZipCode());
	    shelter.setShelterAddress(dto.getShelterAddress());
	    shelter.setShelterAddressDetail(dto.getShelterAddressDetail());
	    shelter.setShelterDescription(dto.getShelterDescription());

	    // 타입에 따른 분기 입력
	    if (dto.getShelterTypeIdx() == 1) { // 공공
	        shelter.setShelterEmail(dto.getShelterEmail());
	        shelter.setShelterBusinessNumber(dto.getShelterBusinessNumber());
	    } else if (dto.getShelterTypeIdx() == 2) { // 민간 사업자 유
	        shelter.setShelterBusinessNumber(dto.getShelterBusinessNumber());
	        shelter.setShelterBusinessFile(dto.getShelterBusinessFile());
	    }

	    // 5. SHELTERS 테이블 insert
	    signupMapper.insertShelter(shelter);
	}
}
