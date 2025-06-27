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
public class BoardCommentReplyRequestDTO {
	@NotNull(message = "회원 번호는 필수 입니다.")
	@Positive(message = "회원 번호는 양수만 허용합니다.")
	private Integer userIdx;
	@NotNull(message = "게시판 번호는 필수 입니다.")
	@Positive(message = "게시판 번호는 양수만 허용합니다.")
	private Integer boardIdx;
	@NotBlank(message = "댓글내용은 필수입니다.")
	private String content;
	@NotNull(message = "ref가 null입니다.")
	private Integer ref;
	@NotNull(message = "turn값이 null입니다.")
	private Integer turn;
}
