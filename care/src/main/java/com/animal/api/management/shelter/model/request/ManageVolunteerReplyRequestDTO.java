package com.animal.api.management.shelter.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageVolunteerReplyRequestDTO {


	private Integer reviewIdx;

	private Integer userIdx;

	private Integer volunteerIdx;
	
	@NotBlank(message = "답글 내용을 작성해주세요.")
	private String content;

	private Integer ref;

	private int lev;

	private int turn;

}
