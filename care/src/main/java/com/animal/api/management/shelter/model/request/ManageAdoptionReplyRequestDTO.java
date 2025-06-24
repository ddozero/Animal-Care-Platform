package com.animal.api.management.shelter.model.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageAdoptionReplyRequestDTO {
	
	@NotNull
	private int reviewIdx;
	
	
	
}
