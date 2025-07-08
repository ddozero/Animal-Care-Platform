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
	

	private Integer reviewIdx;
	
	private int userIdx;
	
	private Integer animalIdx;
	
	@NotBlank(message = "답글 내용을 작성해주세요.")
	private String content;
	
	private int ref;
	
	private int lev;
	
	private int turn;
	
	
	
}
