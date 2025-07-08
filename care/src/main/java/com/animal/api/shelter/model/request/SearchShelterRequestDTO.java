package com.animal.api.shelter.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchShelterRequestDTO {
	private int listSize;
	private int cp;
	private String shelterName;
	private String shelterAddress;
	private String shelterType;
}
