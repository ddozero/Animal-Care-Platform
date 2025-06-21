package com.animal.api.shelter.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllShelterListResponseDTO {
	private int idx;
	private String shelterName;
	private String shelterAddress;
	private String shelterType;
}
