package com.animal.api.admin.shelter.model.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRejectionMailRequestDTO {

	@NotBlank(message = "받는 사람은 필수입니다.")
	String to;
	@NotBlank(message = "메일 제목은 필수입니다.")
	String subject;
	@NotBlank(message = "메일 내용은 필수입니다.")
	String text;
}
