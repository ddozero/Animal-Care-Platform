package com.animal.api.management.shelter.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterBoardRequestDTO {
	
	private Integer userIdx;
	
	@NotBlank(message = "제목 입력은 필수입니다.")
	private String title;

	@NotBlank(message = "본문 입력은 필수입니다.")
	private String content;

	private int ref;
	
	private int idx; //DB 등록시 생성되는 idx 값

}
