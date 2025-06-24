package com.animal.api.management.shelter.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCheckRequestDTO {
	
	private int reviewIdx;
	private int userIdx;
	private int volunteerIdx;
	private int animalIdx;

}
