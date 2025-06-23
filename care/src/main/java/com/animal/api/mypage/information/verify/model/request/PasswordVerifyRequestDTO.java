package com.animal.api.mypage.information.verify.model.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordVerifyRequestDTO {

	@NotBlank(message = "비밀번호 입력은 필수 입니다.")
	private String password;
}
