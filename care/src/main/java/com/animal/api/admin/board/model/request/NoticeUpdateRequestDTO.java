package com.animal.api.admin.board.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUpdateRequestDTO {

	// 서비스에서 세팅
	private Integer idx;
	
	@NotNull(message = "게시글 제목은 필수입니다.")
	@NotBlank(message = "게시글 제목을 적어주세요.")
	@Size(max = 100, message = "제목을 100자 이내로 정해주세요.")
	private String title;
	
	@NotNull(message = "게시글 본문은 필수입니다.")
	@NotBlank(message = "게시글 본문을 적어주세요.")
	@Size(max = 2000, message = "본문을 2000자 이내로 정해주세요.")
	private String content;

}
