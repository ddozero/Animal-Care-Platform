package com.animal.api.find.model.request;

import lombok.Data;

/**
 * 비밀번호 재설정 요청 DTO
 */
@Data
public class FindUserPasswordResetRequestDTO {

	private String userid;
	private String newPassword;
}
