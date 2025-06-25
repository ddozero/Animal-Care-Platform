package com.animal.api.management.shelter.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageAdoptionReplyRequestDTO {
	
	@NotNull(message = "리뷰 IDX는 필수입니다.")
	private Integer reviewIdx;
	
	@NotNull(message = "유저 IDX는 필수입니다.")
	private Integer userIdx;
	
	@NotNull(message = "입양 동물 IDX는 필수입니다.")
	private Integer animalIdx;
	
	@NotBlank(message = "답글 내용 작성은 필수입니다.")
	private String content;
	
	@NotNull(message = "참조할 ref번호는 필수입니다.")
	private Integer ref;
	
	private int lev;
	
	private int turn;
	
	
	
}
