package com.animal.api.signup.service;

import java.time.LocalDate;

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

@Service
@Primary
public class ShelterSignupServiceImple implements ShelterSignupService {

	@Autowired
	private SignupMapper signupMapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	//보호시설 사용자 회원가입 Service
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void signupShelter(ShelterSignupRequestDTO dto) {

	    // 1. [USERS] 관련 유효성 검사
	    if (signupMapper.isDuplicateId(dto.getId()) > 0) {
	        throw new CustomException(409, "이미 사용 중인 아이디입니다.");
	    }
	    if (signupMapper.isDuplicateEmail(dto.getEmail())> 0) {
	        throw new CustomException(409, "이미 사용 중인 이메일입니다.");
	    }
	    if (signupMapper.isDuplicateNickname(dto.getNickname()) > 0) {
	        throw new CustomException(409, "이미 사용 중인 닉네임입니다.");
	    }

	    // 2. [SHELTERS] 관련 유효성 검사 (선택 사항: email 도메인, 사업자번호 포맷 등)
	    switch(dto.getShelterTypeIdx()) {
	    	case 1: 
	    		if (dto.getShelterEmail() == null || !dto.getShelterEmail().matches(
	    			    "^[A-Za-z0-9._%+-]+@([A-Za-z0-9.-]+\\.)?(go\\.kr|korea\\.kr|or\\.kr)$"
	    			)) {
	    			    throw new CustomException(400, 
	    			        "공공 보호소는 go.kr, korea.kr, or.kr 도메인의 이메일이 필요합니다");
	    			}
	            if (dto.getShelterBusinessNumber() == null || dto.getShelterBusinessNumber().isBlank()) {
	                throw new CustomException(400, "공공 보호소는 사업자등록번호가 필요합니다");
	            }
	            break;
	            
	    	case 2:
	    		if(dto.getShelterBusinessNumber() == null || dto.getShelterBusinessNumber().isBlank()) {
	    			throw new CustomException(400, "민간 보호소는 사업자 등록 번호가 필요합니다.");
	    		}
	    		if(dto.getShelterBusinessFile() == null) {
	    			throw new CustomException(400, "민간 보호소는 사업자등록증 파일이 필요합니다.");
	    		}
	    		break;
	    		
	    	case 3: 
	    		break;
	    	default: 
	    		throw new CustomException(400, "올바르지 않은 보호시설 유형입니다.");
	    }
	    
		LocalDate parsedBirthDate = LocalDate.parse(dto.getBirthDate());
		
	    // 3. USER VO 생성
	    UserVO user = new UserVO();
	    user.setUserTypeIdx(2); // 보호시설 고정
	    user.setId(dto.getId());
	    user.setEmail(dto.getEmail());
	    user.setPassword(passwordEncoder.encode(dto.getPassword()));
	    user.setName(dto.getName());
	    user.setNickname(dto.getNickname());
	    user.setBirthDate(parsedBirthDate);
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
