package com.animal.api.board.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchRequestDTO {
	private String type;
	private String keyword;
	@NotNull(message = "보여줄 게시글수는 필수 입니다.")
	@Positive(message = "보여줄 게시글수는 양수만 가능합니다.")
	private Integer listSize;
	@NotNull(message = "현재 페이지 번호는 필수 입니다.")
	@Positive(message = "현재 페이지 번호는 양수만 가능합니다.")
	private Integer cp;

}
