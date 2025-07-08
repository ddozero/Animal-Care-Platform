package com.animal.api.mypage.information.modify.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequestDTO {

	@NotBlank(message = "현재 비밀번호는 필수 입력 항목입니다")
	private String currentPassword;
	
	@NotBlank(message = "새 비밀번호는 필수 입력 항목입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{9,20}$", message = "비밀번호는 영문, 숫자, 특수문자 조합 9~20자여야 합니다.")
	private String newPassword;
	
	@NotBlank(message = "새 비밀번호 확인은 필수 입력 항목입니다.")
	private String confirmPassword;
	
	@NotBlank(message = "자동입력방지 인증 값은 필수 입력 항목입니다.")
	private String captcha;
}
