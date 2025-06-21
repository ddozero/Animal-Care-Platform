package com.animal.api.find.model.request;

import lombok.Data;

/**
 * 비밀번호 재설정 전, 이름 + 이메일 + 인증번호 검증 요청 DTO
 * @author 
 * @since 2025-06-21
 */
@Data
public class FindUserPasswordVerifyRequestDTO {

	private String name;
	private String email;
	private String code;
}
