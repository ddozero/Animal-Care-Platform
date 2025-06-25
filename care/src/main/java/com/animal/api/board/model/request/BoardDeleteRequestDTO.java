package com.animal.api.board.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDeleteRequestDTO {
	@NotNull(message = "게시글 idx는 필수입니다.")
	@Positive(message = "게시글 idx는 양수만 허용합니다.")
	private Integer idx;
	@NotNull(message = "회원 idx는 필수입니다.")
	@Positive(message = "회원 idx는 양수만 허용합니다.")
	private Integer userIdx;
}
