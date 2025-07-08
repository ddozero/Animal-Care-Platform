package com.animal.api.shelter.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllShelterListResponseDTO {
	private int idx;
	private String shelterName;
	private String shelterAddress;
	private String shelterType;
	private String shelterPersonName;
	private String imagePath;
}
