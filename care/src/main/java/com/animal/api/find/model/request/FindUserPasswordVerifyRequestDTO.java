package com.animal.api.find.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 비밀번호 재설정 전, 이름 + 이메일 + 인증번호 검증 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindUserPasswordVerifyRequestDTO {

	@NotBlank(message = "아이디는 필수입니다.")
	private String userid;

	@NotBlank(message = "이름은 필수입니다.")
	private String name;

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "올바른 이메일 형식을 입력해주세요.")
	private String email;

	@NotBlank(message = "인증코드는 필수입니다.")
	private String code;
}
