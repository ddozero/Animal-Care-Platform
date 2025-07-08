package com.animal.api.board.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardCommentUpdateRequestDTO {
	@NotNull(message = "댓글 번호는 필수입니다.")
	@Positive(message = "댓글 번호는 양수만 허용합니다.")
	private Integer idx;
	@NotNull(message = "회원 번호는 필수입니다.")
	@Positive(message = "회원 번호는 양수만 허용합니다.")
	private Integer userIdx;
	@NotNull(message = "게시글 번호는 필수입니다.")
	@Positive(message = "게시글 번호는 양수만 허용합니다.")
	private Integer boardIdx;
	@NotBlank(message = "댓글내용은 필수입니다.")
	private String content;
}
