package com.animal.api.board.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardWriteRequestDTO {
	@NotNull(message = "회원 번호는 필수 입니다.")
	@Min(value = 1, message = "회원 번호는 1이상이어야 합니다.")
	private Integer userIdx;
	@NotBlank(message = "제목 입력은 필수입니다.")
	private String title;
	@NotBlank(message = "본문 입력은 필수입니다.")
	private String content;
	private int ref;
}
