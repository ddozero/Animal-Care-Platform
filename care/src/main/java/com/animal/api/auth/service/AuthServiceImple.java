package com.animal.api.auth.service;

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
}
