package com.animal.api.find.model.request;

import lombok.Data;

/**
 * 비밀번호 재설정 전, 이름 + 이메일 + 인증번호 검증 요청 DTO
 */
@Data
public class FindUserPasswordVerifyRequestDTO {

	private String userid;
	private String name;
	private String email;
	private String code;
}
