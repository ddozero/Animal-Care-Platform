package com.animal.api.mypage.information.modify.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailChangeRequestDTO {

	@NotBlank(message = "현재 이메일 입력은 필수 항목입니다.")
	@Email(message = "현재 이메일 입력한 형식이 올바르지 않습니다.")
	private String currentEmail;
	
	@NotBlank(message = "새 이메일 입력은 필수 항목입니다.")
	@Email(message = "새 이메일 입력한 형식이 올바르지 않습니다.")
	private String newEmail;
	
	@NotBlank(message = "인증번호 입력은 필수입니다.")
	private String code;
}
